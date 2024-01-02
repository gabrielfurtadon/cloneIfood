package com.gabriel.delivery.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository repository;
	
	
	@GetMapping
	public List<Cozinha> listar() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Cozinha> buscar(@PathVariable Long id) {
		return repository.findById(id);
	}
	
}
