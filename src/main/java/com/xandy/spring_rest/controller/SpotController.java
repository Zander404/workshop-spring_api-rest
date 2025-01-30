package com.xandy.spring_rest.controller;


import com.xandy.spring_rest.dto.ParkingCreateDTO;
import com.xandy.spring_rest.dto.SpotCreateDTO;
import com.xandy.spring_rest.dto.SpotResponseDTO;
import com.xandy.spring_rest.dto.mapper.SpotMapper;
import com.xandy.spring_rest.entities.Spot;
import com.xandy.spring_rest.exceptions.ErrorMessage;
import com.xandy.spring_rest.services.SpotServices;
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
@RequestMapping("api/v1/parking_spot")
public class SpotController {

    private final SpotServices services;



    @Operation(summary = "Create a new Parking Slot", description = "Create a new Parking Slot | NEED AN BEARER TOKEN",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Parking Slot Created with SUCCESS",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = SpotResponseDTO.class))
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
    public ResponseEntity<Void> create(@RequestBody @Valid SpotCreateDTO dto) {
        Spot spot = SpotMapper.toSpot(dto);
        services.save(spot);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequestUri().path("/{code}")
                .buildAndExpand(spot.getCode()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SpotResponseDTO> getByCode(@PathVariable String code) {
        Spot spot = services.searchByCode(code);

        return ResponseEntity.ok(SpotMapper.toDto(spot));
    }


}
