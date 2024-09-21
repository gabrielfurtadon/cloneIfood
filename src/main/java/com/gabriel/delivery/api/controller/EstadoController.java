package com.gabriel.delivery.api.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.delivery.api.assembler.EstadoDTOAssembler;
import com.gabriel.delivery.api.assembler.EstadoInputDTODisassembler;
import com.gabriel.delivery.api.model.EstadoDTO;
import com.gabriel.delivery.api.model.input.EstadoInputDTO;
import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.delivery.domain.model.Estado;
import com.gabriel.delivery.domain.repository.EstadoRepository;
import com.gabriel.delivery.domain.service.EstadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	EstadoRepository repository;
	
	@Autowired
	EstadoService service;
	
	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;

	@Autowired
	private EstadoInputDTODisassembler estadoInputDTODisassembler;
	
	@GetMapping
	public List<EstadoDTO> listar() {
	    List<Estado> todosEstados = repository.findAll();
	    
	    return estadoDTOAssembler.toCollectionModel(todosEstados);
		}
	
	@GetMapping("/{id}")
	public ResponseEntity<EstadoDTO> buscar(@PathVariable Long id) {
		
		Optional<Estado> oestado = repository.findById(id);
		
		if(oestado.isPresent()) {
			Estado estado = oestado.get();
			
			return ResponseEntity.ok().body(estadoDTOAssembler.toModel(estado));
		}else {
			return ResponseEntity.notFound().build();
		}
		
		
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@RequestBody @Valid EstadoInputDTO estadoInput) {
		try{
			
		    Estado estado = estadoInputDTODisassembler.toDomainObject(estadoInput);
		    
		    estado = service.salvar(estado);
		    
			return  ResponseEntity.status(HttpStatus.CREATED).body(estadoDTOAssembler.toModel(estado));
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Estado estado) {
		
		try {
			return  ResponseEntity.ok().body(service.atualizar(id, estado));
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		try {
			service.excluir(id);
			return ResponseEntity.noContent().build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
}
