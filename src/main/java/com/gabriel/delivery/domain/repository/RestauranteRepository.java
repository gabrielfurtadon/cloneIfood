package com.gabriel.delivery.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gabriel.delivery.domain.model.Restaurante;
import com.gabriel.delivery.domain.repository.querys.RestauranteRepositoryQuerys;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>,RestauranteRepositoryQuerys, JpaSpecificationExecutor<Restaurante>{

	@Query("from Restaurante r join fetch r.cozinha")
	List<Restaurante> findAll();
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaMin, BigDecimal taxaMax);
	
	//@Query("from Restaurante where nome like %:nome%")
	List<Restaurante> consultaPorNome(String nome);
	

}
