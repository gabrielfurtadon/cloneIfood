package com.gabriel.delivery.domain.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gabriel.delivery.domain.exception.CozinhaNaoEncontradaException;
import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.repository.CozinhaRepository;

import jakarta.transaction.Transactional;

@Service
public class CozinhaService {

	private static final String MSG_COZINHA_EM_USO = "cozinha de código %d não pode ser removida pois está em uso";

	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Não existe cadastro de cozinha com o código %d";
	
	@Autowired
	CozinhaRepository repository;
	
	@Transactional
	public Cozinha adicionar(Cozinha cozinha) {
		return repository.save(cozinha);
	}
	
	@Transactional
	public Cozinha atualizar(Cozinha cozinha) {
		return repository.saveAndFlush(cozinha);
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			repository.deleteById(id);
			repository.flush();
		}catch(NoSuchElementException e) {
			throw new CozinhaNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
		}catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
		}
	}
	
	public Cozinha buscarOuException(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(String.
						format(MSG_COZINHA_NAO_ENCONTRADA, id)));
	}
	
}
