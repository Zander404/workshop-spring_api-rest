package com.xandy.spring_rest.controller;


import com.xandy.spring_rest.dto.ClientCreateDTO;
import com.xandy.spring_rest.dto.ParkingCreateDTO;
import com.xandy.spring_rest.dto.ParkingResponseDTO;
import com.xandy.spring_rest.dto.mapper.ParkingMapper;
import com.xandy.spring_rest.entities.Parking;
import com.xandy.spring_rest.exceptions.ErrorMessage;
import com.xandy.spring_rest.services.ParkingServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/parking")
public class ParkingController {

    private final ParkingServices services;



    @Operation(summary = "Create a new Parking Slot", description = "Create a new Parking Slot | NEED AN BEARER TOKEN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Parking Slot Created with SUCCESS",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ParkingResponseDTO.class))
                    ),

                    @ApiResponse(responseCode = "403",
                            description = "User can't register a Parking Slot, PERMISSION INVALID",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid ParkingCreateDTO dto) {
        Parking parking = ParkingMapper.toParking(dto);
        services.save(parking);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(parking.getCode()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParkingResponseDTO> getByCode(@PathVariable String code) {
        Parking parking = services.searchByCode(code);

        return ResponseEntity.ok(ParkingMapper.toDto(parking));
    }


}
