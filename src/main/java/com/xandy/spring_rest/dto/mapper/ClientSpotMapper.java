package com.xandy.spring_rest.dto.mapper;

import com.xandy.spring_rest.dto.ParkingCreateDTO;
import com.xandy.spring_rest.dto.ParkingResponseDTO;
import com.xandy.spring_rest.entities.ClientSpot;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientSpotMapper {

    public static ClientSpot toClientSpot(@Valid ParkingCreateDTO clientParkingSpot) {
        return new ModelMapper().map(clientParkingSpot, ClientSpot.class);
    }


    public static ParkingResponseDTO toDto(ClientSpot clientSpot) {
        return new ModelMapper().map(clientSpot, ParkingResponseDTO.class);
    }


}
