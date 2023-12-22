package com.gabriel.delivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.delivery.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{

}
