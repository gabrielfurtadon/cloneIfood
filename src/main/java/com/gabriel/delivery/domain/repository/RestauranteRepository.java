package com.gabriel.delivery.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gabriel.delivery.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, JpaSpecificationExecutor<Restaurante>{

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaMin, BigDecimal taxaMax);
	
	//@Query("from Restaurante where nome like %:nome%")
	List<Restaurante> consultaPorNome(String nome);
	
	List<Restaurante> find(String nome, BigDecimal taxaFreteMin, BigDecimal taxaFreteMax);
}
