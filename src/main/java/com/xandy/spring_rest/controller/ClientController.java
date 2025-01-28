package com.xandy.spring_rest.controller;

import com.xandy.spring_rest.dto.ClientCreateDTO;
import com.xandy.spring_rest.dto.ClientResponseDTO;
import com.xandy.spring_rest.dto.PageableDTO;
import com.xandy.spring_rest.dto.mapper.ClientMapper;
import com.xandy.spring_rest.dto.mapper.PageableMapper;
import com.xandy.spring_rest.entities.Client;
import com.xandy.spring_rest.exceptions.ErrorMessage;
import com.xandy.spring_rest.jwt.JwtUserDetails;
import com.xandy.spring_rest.repository.projection.ClientProjection;
import com.xandy.spring_rest.services.ClientService;
import com.xandy.spring_rest.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@Tag(name = "Clients", description = "Has all relative function to Create , Read, Update and Delete an Client.")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/clients")
public class ClientController {


    private final ClientService services;
    private final UserService userService;


    @Operation(summary = "Create a new Client", description = "Create a new Client | NEED AN BEARER TOKEN",
            security = @SecurityRequirement(name = "security"),
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
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ClientResponseDTO> create(@RequestBody @Valid ClientCreateDTO dto,
                                                    @AuthenticationPrincipal JwtUserDetails userDetails) {

        Client client = ClientMapper.toClient(dto);
        client.setUser(userService.findById(userDetails.getId()));
        services.save(client);


        return ResponseEntity.status(201).body(ClientMapper.toDto(client));

    }


    @Operation(summary = "List ALL Clients", description = "List all Client in Application | NEED AN BEARER TOKEN | ROLE:  ADMIN",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "The Page Actual Page"
                    ),

                    @Parameter(in = QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "20")),
                            description = "The Size of item in a Page"
                    ),

                    @Parameter(in = QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "id,asc")),
                            description = "The Page Actual Page"
                    )

            },
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "List of All Client registered",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClientCreateDTO.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Not has any Client registered",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
            })
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDTO> listAll(@Parameter(hidden = true) @PageableDefault(size=5 , sort = {"name"} ) Pageable pageable) {
        Page<ClientProjection> listClients = services.findAll(pageable);

        return ResponseEntity.ok(PageableMapper.toDTO(listClients));
    }

    @Operation(summary = "GET an Client", description = "Get a Client by Id | NEED AN BEARER TOKEN | ROLE: ADMIN ",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Client is Found",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ClientCreateDTO.class))
                    ),
                    @ApiResponse(responseCode = "403",
                            description = "Forbidden to ROLE USER, PERMISSION NEEDED is ADMIN",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Client with this ID not found",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
        Client client = services.findById(id);
        return ResponseEntity.ok().body(ClientMapper.toDto(client));
    }
}


