package com.gabriel.delivery.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaInputDTO {

    @NotBlank
    private String nome;
    
}
