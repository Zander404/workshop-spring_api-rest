package com.xandy.spring_rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotCreateDTO {

    @NotBlank(message = "{NotBlank.spotCreateDTO.code}")
    @Size(min=4, max=4, message = "{Size.spotCreateDTO.code}")
    private String code;

    @NotBlank( message = "{NotBlank.spotCreateDTO.status}")
    @Pattern(regexp = "FREE|OCCUPIED", message = "{Pattern.spotCreateDTO.status}")
    private String status;

}
