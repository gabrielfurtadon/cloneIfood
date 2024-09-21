package com.gabriel.delivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.delivery.api.assembler.CozinhaDTOAssembler;
import com.gabriel.delivery.api.assembler.CozinhaInputDTODisassembler;
import com.gabriel.delivery.api.model.CozinhaDTO;
import com.gabriel.delivery.api.model.input.CozinhaInputDTO;
import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.repository.CozinhaRepository;
import com.gabriel.delivery.domain.service.CozinhaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository repository;
	
	@Autowired
	CozinhaService service;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDTODisassembler cozinhaInputDisassembler; 
	
	@GetMapping
	public List<CozinhaDTO> listar() {
	    List<Cozinha> todasCozinhas = repository.findAll();
	    
	    return cozinhaModelAssembler.toCollectionDTO(todasCozinhas);
	}
	
	@GetMapping("/{id}")
	public CozinhaDTO buscar(@PathVariable Long id) {
	    Cozinha cozinha = service.buscarOuException(id);
	    
	    return cozinhaModelAssembler.toDTO(cozinha);
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInput){
		
		   Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		   cozinha = service.adicionar(cozinha);
		    
		   return cozinhaModelAssembler.toDTO(cozinha);
	}
	
	@PutMapping("{id}")
	public CozinhaDTO atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaInputDTO cozinhaInput) {
		
		Cozinha cozinhaAtual = service.buscarOuException(id);
	    cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
	    cozinhaAtual = service.atualizar(cozinhaAtual);
	    
	    return cozinhaModelAssembler.toDTO(cozinhaAtual);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
			service.remover(id);

	}
	
	@GetMapping("/nome/{nome}")
	public List<Cozinha> buscarPorNome(@PathVariable String nome) {
		return repository.findByNomeContaining(nome);
	}
	
	
}
