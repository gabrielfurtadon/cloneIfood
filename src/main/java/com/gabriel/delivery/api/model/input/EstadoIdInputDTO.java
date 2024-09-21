package com.gabriel.delivery.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EstadoIdInputDTO {

    @NotNull
    private Long id;
    
}