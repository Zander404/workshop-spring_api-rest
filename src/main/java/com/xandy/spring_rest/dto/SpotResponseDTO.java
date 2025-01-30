package com.xandy.spring_rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpotResponseDTO {
    private Long id;
    private String code;
    private String status;
}
