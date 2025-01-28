package com.xandy.spring_rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkingCreateDTO {

    @NotBlank
    @Size(min=4, max=4)
    private String code;

    @NotBlank
    @Pattern(regexp = "FREE|OCCUPIED")
    private String status;

}
