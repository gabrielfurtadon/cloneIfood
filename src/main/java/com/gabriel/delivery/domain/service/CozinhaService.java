package com.gabriel.delivery.domain.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private static final String MSG_COZINHA_EM_USO = "cozinha de código %d não pode ser removida pois está em uso";

	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe cadastro de cozinha com o código %d";
	
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
			repository.deleteById(id);
		}catch(NoSuchElementException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
		}catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
		}
	}
	
	public Cozinha buscarOuException(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException(String.
						format(MSG_COZINHA_NAO_ENCONTRADA, id)));
	}
	
}
