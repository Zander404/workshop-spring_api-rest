package com.xandy.spring_rest.controller;


import com.xandy.spring_rest.entities.User;
import com.xandy.spring_rest.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService services;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User obj) {
        User user = services.save(obj);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
