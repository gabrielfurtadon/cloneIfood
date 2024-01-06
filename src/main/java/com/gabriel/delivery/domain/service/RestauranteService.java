package com.gabriel.delivery.domain.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.delivery.domain.model.Restaurante;
import com.gabriel.delivery.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	RestauranteRepository repository;

	public Restaurante salvar(Restaurante restaurante) {
		return repository.save(restaurante);
	}
	
	public Restaurante atualizar(Restaurante restaurante){
		return repository.saveAndFlush(restaurante);
	}
	
	public void remover(Long id) {
		try {
			Optional<Restaurante> orestaurante = repository.findById(id);
			
			Restaurante restaurante = orestaurante.get();
			repository.delete(restaurante);
			
		}catch(NoSuchElementException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de restaurante com o código %d", id));
		}catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException(String.format("restaurante de código %d não pode ser removida pois está em uso", id));
		}
	}
	
}
