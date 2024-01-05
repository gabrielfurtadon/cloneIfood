package com.gabriel.delivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	CozinhaRepository repository;
	
	public Cozinha adicionar(Cozinha cozinha) {
		return repository.save(cozinha);
	}
	
}
