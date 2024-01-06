package com.gabriel.delivery.domain.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	CozinhaRepository repository;
	
	public Cozinha adicionar(Cozinha cozinha) {
		return repository.save(cozinha);
	}
	
	public Cozinha atualizar(Cozinha cozinha) {
		return repository.saveAndFlush(cozinha);
	}
	
	public void remover(Long id) {
		try {
			Optional<Cozinha> ocozinha = repository.findById(id);

				Cozinha cozinha = ocozinha.get();
				repository.delete(cozinha);
		}catch(NoSuchElementException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com o código %d", id));
		}catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException(String.format("cozinha de código %d não pode ser removida pois está em uso", id));
		}
	}
	
}
