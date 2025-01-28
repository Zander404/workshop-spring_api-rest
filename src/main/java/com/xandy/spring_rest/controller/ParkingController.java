package com.xandy.spring_rest.controller;


import com.xandy.spring_rest.dto.ParkingCreateDTO;
import com.xandy.spring_rest.dto.ParkingResponseDTO;
import com.xandy.spring_rest.dto.mapper.ParkingMapper;
import com.xandy.spring_rest.entities.Parking;
import com.xandy.spring_rest.services.ParkingServices;
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
