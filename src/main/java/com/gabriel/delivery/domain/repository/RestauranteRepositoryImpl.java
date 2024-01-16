package com.gabriel.delivery.domain.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.gabriel.delivery.domain.model.Restaurante;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class RestauranteRepositoryImpl {

	@PersistenceContext
	private EntityManager manager;
	
	public List<Restaurante> find(String nome, BigDecimal taxaFreteMin, BigDecimal taxaFreteMax) {
		
		var jpql = new StringBuilder();
		jpql.append("from Restaurante where 0 = 0");
				
		var parametros = new HashMap<String, Object>();
		
		if(StringUtils.hasLength(nome)) {
			jpql.append("and nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}
		
		if(taxaFreteMin != null) {
			jpql.append("and taxaFrete >= :taxaFreteMin ");
			parametros.put("taxaFreteMin", "taxaFreteMin");
		}
		
		if(taxaFreteMax != null) {
			jpql.append("and taxaFrete <= :taxaFreteMax ");
			parametros.put("taxaFreteMax", "taxaFreteMax");
		}
		
		TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
		
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
		
		return query.getResultList();
		
	}
	
	
}
