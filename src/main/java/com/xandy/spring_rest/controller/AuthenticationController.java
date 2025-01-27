package com.xandy.spring_rest.controller;

import com.xandy.spring_rest.dto.UserLoginDTO;
import com.xandy.spring_rest.dto.UserResponseDTO;
import com.xandy.spring_rest.exceptions.ErrorMessage;
import com.xandy.spring_rest.jwt.JwtToken;
import com.xandy.spring_rest.jwt.JwtUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Process to users login in the application")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Authentication an User", description = " Get the JWT/BEARED TOKEN to authenticate the session | REQUIRED: NONE | PERMISSION: NONE",
            security =  @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = " List of User in database",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))),
            })
    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody @Valid UserLoginDTO dto, HttpServletRequest request) {
        log.info("Authenticating user {}", dto.getUsername());
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken jwtToken = detailsService.getTokenAuthenticated(dto.getUsername());
            return ResponseEntity.ok(jwtToken);

        } catch (AuthenticationException ex) {
            log.warn("Bad Credentials to usename {} ", dto.getUsername());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid Credentials!"));

    }


}
