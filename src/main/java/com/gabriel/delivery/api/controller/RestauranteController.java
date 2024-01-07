package com.gabriel.delivery.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.delivery.domain.model.Restaurante;
import com.gabriel.delivery.domain.repository.RestauranteRepository;
import com.gabriel.delivery.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	RestauranteRepository repository;
	
	@Autowired
	RestauranteService service;
	
	@GetMapping
	public List<Restaurante> listar() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Restaurante>> buscar(@PathVariable Long id){
		
		Optional<Restaurante> cozinha = repository.findById(id);
		if(cozinha.isPresent()) {
			return ResponseEntity.ok().body(cozinha);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(restaurante));  
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante){
		try {
			return ResponseEntity.ok().body(service.atualizar(id, restaurante));
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		
	}
	
}
