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

import com.gabriel.delivery.api.assembler.CidadeDTOAssembler;
import com.gabriel.delivery.api.assembler.CidadeInputDTODisassembler;
import com.gabriel.delivery.api.model.CidadeDTO;
import com.gabriel.delivery.api.model.input.CidadeInputDTO;
import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.delivery.domain.model.Cidade;
import com.gabriel.delivery.domain.repository.CidadeRepository;
import com.gabriel.delivery.domain.service.CidadeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	CidadeRepository repository;
	
	@Autowired
	CidadeService service;
	
	@Autowired
	private CidadeDTOAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDTODisassembler cidadeInputDisassembler; 
	
	
	@GetMapping
	public List<CidadeDTO> listar() {
		List<Cidade> todasCidades = repository.findAll();
	    
	    return cidadeModelAssembler.toCollectionModel(todasCidades);
		}
	
	@GetMapping("/{id}")
	public ResponseEntity<CidadeDTO> buscar(@PathVariable Long id) {
		
		Optional<Cidade> ocidade = repository.findById(id);
		
		if(ocidade.isPresent()) {
			Cidade cidade = ocidade.get();
			return ResponseEntity.ok().body(cidadeModelAssembler.toModel(cidade));
		}else {
			return ResponseEntity.notFound().build();
		}
		
		//
	}
	
	@PostMapping
	public ResponseEntity<?> criar(@RequestBody @Valid CidadeInputDTO cidadeInput) {
		try{
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
	        
	        cidade = service.salvar(cidade);
	        
			return  ResponseEntity.status(HttpStatus.CREATED).body(cidadeModelAssembler.toModel(cidade));
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Cidade cidade) {
		
		try {
			return  ResponseEntity.ok().body(service.atualizar(id, cidade));
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
