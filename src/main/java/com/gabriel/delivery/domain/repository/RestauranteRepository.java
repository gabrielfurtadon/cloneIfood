package com.gabriel.delivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.delivery.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{

}
