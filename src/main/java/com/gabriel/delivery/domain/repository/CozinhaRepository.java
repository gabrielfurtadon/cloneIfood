package com.gabriel.delivery.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.delivery.domain.model.Cozinha;

public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{

	List<Cozinha> findByNomeContaining(String nome);
	
	
}
