package com.xandy.spring_rest.dto.mapper;

import com.xandy.spring_rest.dto.ClientCreateDTO;
import com.xandy.spring_rest.dto.ClientResponseDTO;
import com.xandy.spring_rest.entities.Client;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMapper {

    public static Client toClient(ClientCreateDTO dto) {
        return new ModelMapper().map(dto, Client.class);
    }

    public static ClientResponseDTO toDto(Client client) {
        return new ModelMapper().map(client, ClientResponseDTO.class);
    }

    public static List<ClientResponseDTO> toListDto(List<Client> listClients) {
        return listClients.stream().map(ClientMapper::toDto).collect(Collectors.toList());
    }
}
