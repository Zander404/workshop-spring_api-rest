package com.xandy.spring_rest.controller;


import com.xandy.spring_rest.dto.UserCreateDTO;
import com.xandy.spring_rest.dto.UserPasswordDTO;
import com.xandy.spring_rest.dto.UserResponseDTO;
import com.xandy.spring_rest.dto.mapper.UserMapper;
import com.xandy.spring_rest.entities.User;
import com.xandy.spring_rest.exceptions.ErrorMessage;
import com.xandy.spring_rest.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Users", description = "Has all relative function to Create , Read, Update and Delete an User.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService services;

    @Operation(summary = "Get All Users", description = "Search all user's saved REQUIRED BEARED TOKEN | PERMISSION ADMIN",
            security =  @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = " List of User in database",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))),
            })
    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = services.getAll();
        return ResponseEntity.ok().body(UserMapper.toListDto(users));
    }

    @Operation(summary = "Get a User by his ID", description = "Search in the Database a User with the Same ID value. REQUIRED BEARED TOKEN | PERMISSION ADMIN|USER",
            security =  @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),

                    @ApiResponse(responseCode = "404", description = "User with that id, is not find in the system. ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

            })

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') (hasRole('USER' AND #id == authentication.principal.id))")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        User obj = services.findById(id);
        return ResponseEntity.ok().body(UserMapper.toDto(obj));
    }


    @Operation(summary = "Create a New User", description = "Create a new User in the System. ",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User Create with success",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),

                    @ApiResponse(responseCode = "409", description = "User already register in the database",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "422", description = "Resource  is not process by the system, invalid data informed!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            })

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO obj) {
        User user = services.save(UserMapper.toUser(obj));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(user));
    }

    @Operation(summary = "Update the Password", description = "Update the User Password. REQUIRED BEARED TOKEN | PERMISSION ADMIN",
            security =  @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Password is Updated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),

                    @ApiResponse(responseCode = "403", description = "User was no permission  ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "404", description = "User with that id, is not find in the system. ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "400", description = "Passwords don't match!. ",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

            })


    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN') AND (#id == authentication.principal.id)")
    public ResponseEntity<UserResponseDTO> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDTO dto) {
        User user = services.updatePassword(id, dto.getPassword(), dto.getNewPassword(), dto.getConfirmPassword());
        return ResponseEntity.noContent().build();
    }

}
