package com.gabriel.delivery.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeIdInputDTO {

    @NotNull
    private Long id;
    
}