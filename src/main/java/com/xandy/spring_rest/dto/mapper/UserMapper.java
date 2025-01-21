package com.xandy.spring_rest.dto.mapper;

import com.xandy.spring_rest.dto.UserCreateDTO;
import com.xandy.spring_rest.dto.UserResponseDTO;
import com.xandy.spring_rest.entities.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;


import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(UserCreateDTO createDTO) {
        return new ModelMapper().map(createDTO, User.class);
    }

    public static UserResponseDTO toDto(User user) {
        String role = user.getRole().name().substring("ROLE_".length());
        PropertyMap<User, UserResponseDTO> props = new PropertyMap<User, UserResponseDTO>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);

        return mapper.map(user, UserResponseDTO.class);
    }

    public static List<UserResponseDTO> toListDto(List<User> user) {

        return user.stream().map(UserMapper::toDto).collect(Collectors.toList());



    }
}

