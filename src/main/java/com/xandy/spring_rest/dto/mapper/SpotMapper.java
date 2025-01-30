package com.xandy.spring_rest.dto.mapper;

import com.xandy.spring_rest.dto.ParkingCreateDTO;
import com.xandy.spring_rest.dto.SpotCreateDTO;
import com.xandy.spring_rest.dto.SpotResponseDTO;
import com.xandy.spring_rest.entities.Spot;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpotMapper {

    public static Spot toSpot(SpotCreateDTO dto){
        return new ModelMapper().map(dto, Spot.class);
    }

    public static SpotResponseDTO toDto(Spot spot){
        return new ModelMapper().map(spot, SpotResponseDTO.class);
    }
}
