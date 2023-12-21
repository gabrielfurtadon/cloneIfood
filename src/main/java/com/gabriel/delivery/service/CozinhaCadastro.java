package com.gabriel.delivery.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gabriel.delivery.domain.model.Cozinha;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Component
public class CozinhaCadastro {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public List<Cozinha> listar() {
		TypedQuery<Cozinha> query = entityManager.createQuery("from tebela_cozinha", Cozinha.class);
		
		return query.getResultList();
	}
	
}
	
