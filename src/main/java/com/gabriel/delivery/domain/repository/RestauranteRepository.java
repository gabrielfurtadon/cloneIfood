package com.gabriel.delivery.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.delivery.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaMin, BigDecimal taxaMax);
	
}
