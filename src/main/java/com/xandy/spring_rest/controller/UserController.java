package com.xandy.spring_rest.controller;


import com.xandy.spring_rest.dto.UserCreateDTO;
import com.xandy.spring_rest.dto.UserPasswordDTO;
import com.xandy.spring_rest.dto.UserResponseDTO;
import com.xandy.spring_rest.dto.mapper.UserMapper;
import com.xandy.spring_rest.entities.User;
import com.xandy.spring_rest.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService services;

    @GetMapping("")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = services.getAll();
        return ResponseEntity.ok().body(UserMapper.toListDto(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        User obj = services.findById(id);
        return ResponseEntity.ok().body(UserMapper.toDto(obj));
    }


    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO obj) {
        User user = services.save(UserMapper.toUser(obj));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDTO dto) {
        User user = services.updatePassword(id, dto.getPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

}
