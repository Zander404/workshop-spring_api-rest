package com.xandy.spring_rest.controller;

import com.xandy.spring_rest.dto.ClientCreateDTO;
import com.xandy.spring_rest.dto.ClientResponseDTO;
import com.xandy.spring_rest.dto.mapper.ClientMapper;
import com.xandy.spring_rest.entities.Client;
import com.xandy.spring_rest.exceptions.ErrorMessage;
import com.xandy.spring_rest.jwt.JwtUserDetails;
import com.xandy.spring_rest.services.ClientService;
import com.xandy.spring_rest.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {


    private final ClientService services;
    private final UserService userService;


    @Operation(summary = "Create a new Client", description = "Create a new Client | NEED AN BEARER TOKEN",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Client Created with SUCCESS",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClientCreateDTO.class))
                    ),
                    @ApiResponse(responseCode = "409",
                            description = "Client CPF already registered in system",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "403",
                            description = "User can't register a Client, PERMISSION INVALID",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
            })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDTO> create(@RequestBody @Valid ClientCreateDTO dto,
                                                    @AuthenticationPrincipal JwtUserDetails userDetails) {

        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.findById(userDetails.getId()));
        services.save(client);

        return ResponseEntity.status(201).body(ClientMapper.toDto(client));

    }
}
