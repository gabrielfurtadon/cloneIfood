package com.gabriel.delivery.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gabriel.delivery.domain.model.Restaurante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class RestauranteRepositoryImpl {

	@PersistenceContext
	private EntityManager manager;
	
	public List<Restaurante> find(String nome, BigDecimal taxaFreteMin, BigDecimal taxaFreteMax) {
		
		var jpql = "from Restaurante where nome like :nome " + "and taxaFrete between :taxaFreteMin and :taxaFreteMax";
		
		return manager.createQuery(jpql, Restaurante.class)
				.setParameter("nome", "%" + nome + "%")
				.setParameter("taxaMin", taxaFreteMin) 
				.setParameter("taxaMax", taxaFreteMax)
				.getResultList();
		
	}
	
	
}
