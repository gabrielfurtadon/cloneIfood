package com.gabriel.delivery.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.gabriel.delivery.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
}
