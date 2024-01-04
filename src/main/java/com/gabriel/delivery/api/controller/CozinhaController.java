package com.gabriel.delivery.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
		
		return repository.save(cozinha);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		
		Optional<Cozinha> optionalCozinha = repository.findById(id);
		
		if (optionalCozinha.isPresent()) {
	        Cozinha cozinhaAtual = optionalCozinha.get();
	        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
	        repository.saveAndFlush(cozinhaAtual);
	        return ResponseEntity.ok(cozinhaAtual);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long id) {

		try {
			Optional<Cozinha> ocozinha = repository.findById(id);

			if (ocozinha.isPresent()) {
				Cozinha cozinha = ocozinha.get();
				repository.delete(cozinha);

				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (DataIntegrityViolationException error) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}
	
}
