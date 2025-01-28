package com.xandy.spring_rest.dto.mapper;

import com.xandy.spring_rest.dto.ParkingCreateDTO;
import com.xandy.spring_rest.dto.ParkingResponseDTO;
import com.xandy.spring_rest.entities.Parking;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingMapper {

    public static Parking toParking(ParkingCreateDTO dto){
        return new ModelMapper().map(dto, Parking.class);
    }

    public static ParkingResponseDTO toDto(Parking parking){
        return new ModelMapper().map(parking, ParkingResponseDTO.class);
    }
}
