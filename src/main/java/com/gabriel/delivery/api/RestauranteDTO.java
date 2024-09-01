package com.gabriel.delivery.api;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RestauranteDTO {

	private long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaDTO cozinhaDto;
	
}
