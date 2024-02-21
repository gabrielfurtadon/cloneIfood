package com.gabriel.delivery.domain.repository.querys;

import java.math.BigDecimal;
import java.util.List;

import com.gabriel.delivery.domain.model.Restaurante;

public interface RestauranteRepositoryQuerys{

	List<Restaurante> find(String nome, BigDecimal taxaFreteMin, BigDecimal taxaFreteMax);
	
	List<Restaurante> findComFreteGratis(String nome);
}
