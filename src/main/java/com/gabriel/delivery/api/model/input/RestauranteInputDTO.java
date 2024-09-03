package com.gabriel.delivery.api.model.input;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class RestauranteInputDTO {
	
	@NotBlank
	private String nome;
	@NotNull 
	@PositiveOrZero
	private BigDecimal taxaFrete;
	@Valid
	@NotNull
	private CozinhaIdInputDTO cozinhaDto;
	
}
