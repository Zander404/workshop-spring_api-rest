package com.xandy.spring_rest.controller;


import com.xandy.spring_rest.dto.PageableDTO;
import com.xandy.spring_rest.dto.ParkingCreateDTO;
import com.xandy.spring_rest.dto.ParkingResponseDTO;
import com.xandy.spring_rest.dto.mapper.ClientSpotMapper;
import com.xandy.spring_rest.dto.mapper.PageableMapper;
import com.xandy.spring_rest.entities.ClientSpot;
import com.xandy.spring_rest.exceptions.ErrorMessage;
import com.xandy.spring_rest.jwt.JwtUserDetails;
import com.xandy.spring_rest.repository.projection.ClientSpotProjection;
import com.xandy.spring_rest.services.ClientSpotService;
import com.xandy.spring_rest.services.ParkingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/parking")
public class ParkingController {
    private final ParkingService parkingService;
    private final ClientSpotService clientSpotService;


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

    @PostMapping("/check-in/{ticket}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<ParkingResponseDTO> getByTicket(@PathVariable String ticket) {
        ClientSpot clientSpot = clientSpotService.searchByTicket(ticket);
        ParkingResponseDTO responseDto = ClientSpotMapper.toDto(clientSpot);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/check-out/{ticket}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<ParkingResponseDTO> checkout(@PathVariable String ticket) {
        ClientSpot clientSpot = parkingService.checkOut(ticket);
        ParkingResponseDTO responseDto = ClientSpotMapper.toDto(clientSpot);
        return ResponseEntity.ok(responseDto);
    }


    @GetMapping("/cpf/{cpf}}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> getAllParkingByCPF(@PathVariable String cpf, @Parameter(hidden = true)
                                                                 @PageableDefault(size = 5, sort = "checkInDate",
                                                                         direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<ClientSpotProjection> projection = clientSpotService.searchAllByClientCPF(cpf, pageable);
        PageableDTO dto = PageableMapper.toDTO(projection);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PageableDTO> getAllParkingByClient(@AuthenticationPrincipal JwtUserDetails user,
                                                             @PageableDefault(size = 5, sort = "checkInDate",
                                                                            direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<ClientSpotProjection> projection = clientSpotService.searchAllByUserId(user.getId(), pageable);
        PageableDTO dto = PageableMapper.toDTO(projection);
        return ResponseEntity.ok(dto);
    }





}
