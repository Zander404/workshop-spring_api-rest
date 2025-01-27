package com.xandy.spring_rest.dto.mapper;

import com.xandy.spring_rest.dto.ClientCreateDTO;
import com.xandy.spring_rest.dto.ClientResponseDTO;
import com.xandy.spring_rest.entities.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDTO dto) {
        return new ModelMapper().map(dto, Client.class);
    }

    public static ClientResponseDTO toDto(Client client) {
        return new ModelMapper().map(client, ClientResponseDTO.class);
    }
}
