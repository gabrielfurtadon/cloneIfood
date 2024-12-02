package com.gabriel.delivery.api.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormaPagamentoInputDTO {

    @NotBlank
    private String descricao;
    
}  
