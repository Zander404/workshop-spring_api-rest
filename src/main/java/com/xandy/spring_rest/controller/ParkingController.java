package com.xandy.spring_rest.controller;


import com.xandy.spring_rest.dto.ParkingCreateDTO;
import com.xandy.spring_rest.dto.ParkingResponseDTO;
import com.xandy.spring_rest.dto.mapper.ClientSpotMapper;
import com.xandy.spring_rest.entities.ClientSpot;
import com.xandy.spring_rest.exceptions.ErrorMessage;
import com.xandy.spring_rest.services.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {
    private final ParkingService parkingService;


    @Operation(summary = "Check-In Operation", description = "Register Car entry in the Parking Lot. " +
            " NEED AN BEARER TOKEN | Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Create with success",
                            headers = @Header(name = HttpHeaders.LOCATION, description = "Access URL "),
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ParkingResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Probable Causes: <br/>" +
                            "- CPF Don't register ; <br/>" +
                            "- Any Spot Free;",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Invalid Data",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Need Permission ADMIN",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/check-in")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDTO> checkIn(@RequestBody @Valid ParkingCreateDTO dto) {
        ClientSpot clientSpot = ClientSpotMapper.toClientSpot(dto);
        parkingService.checkIn(clientSpot);
        ParkingResponseDTO responseDto = ClientSpotMapper.toDto(clientSpot);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{ticket}")
                .buildAndExpand(clientSpot.getTicket())
                .toUri();

        return ResponseEntity.created(location).body(responseDto);


    }


}
