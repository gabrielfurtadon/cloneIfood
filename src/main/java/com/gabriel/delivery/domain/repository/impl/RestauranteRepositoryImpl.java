package com.gabriel.delivery.domain.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.gabriel.delivery.domain.model.Restaurante;
import com.gabriel.delivery.domain.repository.RestauranteRepository;
import com.gabriel.delivery.domain.repository.querys.RestauranteRepositoryQuerys;
import com.gabriel.delivery.domain.repository.spec.RestauranteSpecs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQuerys{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy //SÓ INSTANCIA NO MOMENTO QUE FOR PRECISO -> EVITAR DEPENDENCIA CIRCULAR
	public RestauranteRepository repository;
	
//	public List<Restaurante> find(String nome, BigDecimal taxaFreteMin, BigDecimal taxaFreteMax) {
		
//		var jpql = new StringBuilder();
//		jpql.append("from Restaurante where 0 = 0");
//				
//		var parametros = new HashMap<String, Object>();
//		
//		if(StringUtils.hasLength(nome)) {
//			jpql.append("and nome like :nome ");
//			parametros.put("nome", "%" + nome + "%");
//		}
//		
//		if(taxaFreteMin != null) {
//			jpql.append("and taxaFrete >= :taxaFreteMin ");
//			parametros.put("taxaFreteMin", "taxaFreteMin");
//		}
//		
//		if(taxaFreteMax != null) {
//			jpql.append("and taxaFrete <= :taxaFreteMax ");
//			parametros.put("taxaFreteMax", "taxaFreteMax");
//		}
//		
//		TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
//		
//		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
//		
//		return query.getResultList();
//		
//	}
	
	public List<Restaurante> find(String nome, BigDecimal taxaFreteMin, BigDecimal taxaFreteMax) {
	
		CriteriaBuilder builder = manager.getCriteriaBuilder(); //fabrica para fazer consultas
		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		
		Root<Restaurante> root = criteria.from(Restaurante.class); //mesma coisa que 'from restaurante' do jpql - Root = from Restaurante
		
		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		
		if(taxaFreteMin != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteMin));
		}
		
		if(taxaFreteMax != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteMax));
		}
		
		criteria.where(predicates.toArray(new Predicate[0])); //passando a instancia de um array (posicao nao importa)
		
		return manager.createQuery(criteria).getResultList();
		
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return repository.findAll(RestauranteSpecs.comFreteGratis()
				.and(RestauranteSpecs.comNomeSemelhante(nome)));
	}
	
	
}
