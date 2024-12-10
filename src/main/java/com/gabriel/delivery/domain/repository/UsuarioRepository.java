package com.gabriel.delivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabriel.delivery.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
