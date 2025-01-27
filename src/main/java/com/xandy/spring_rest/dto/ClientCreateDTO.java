package com.xandy.spring_rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientCreateDTO {

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @Size(min = 11, max = 11)
    @CPF
    private String cpf;

}
