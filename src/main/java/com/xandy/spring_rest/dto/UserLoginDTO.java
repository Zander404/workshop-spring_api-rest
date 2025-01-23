package com.xandy.spring_rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginDTO {
    @NotBlank
    @Email(message = "Email format Invalid")
    private String username;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}
