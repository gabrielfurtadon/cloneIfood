package com.gabriel.delivery.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //Spring nao deve instanciar uma implementação para essa interface
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID>{

	Optional<T> buscarPrimeiro();
	
	//Método criado para não precisar fazer a injecão de dependencia do entityManager direto na classe
	void detach(T entity);
	
}
