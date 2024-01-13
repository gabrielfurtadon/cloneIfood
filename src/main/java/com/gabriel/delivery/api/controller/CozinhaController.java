package com.gabriel.delivery.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.repository.CozinhaRepository;
import com.gabriel.delivery.domain.service.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository repository;
	
	@Autowired
	CozinhaService service;
	
	@GetMapping
	public List<Cozinha> listar() {
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Cozinha>> buscar(@PathVariable Long id) {
		
		Optional<Cozinha> cozinha = repository.findById(id);
		if(cozinha.isPresent()) {
		return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha){
		
		return service.adicionar(cozinha);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		
		Optional<Cozinha> optionalCozinha = repository.findById(id);
		
		if (optionalCozinha.isPresent()) {
	        BeanUtils.copyProperties(cozinha, optionalCozinha.get(), "id");
	        Cozinha cozinhaSalva = service.atualizar(optionalCozinha.get());
	        return ResponseEntity.ok(cozinhaSalva);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		try {
			service.remover(id);
			return ResponseEntity.noContent().build();

		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
		
	}
	
	@GetMapping("/nome/{nome}")
	public List<Cozinha> buscarPorNome(@PathVariable String nome) {
		return repository.findByNome(nome);
	}
	
}
