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

import com.gabriel.delivery.api.assembler.GrupoDTOAssembler;
import com.gabriel.delivery.api.assembler.GrupoDTODisassembler;
import com.gabriel.delivery.api.model.GrupoDTO;
import com.gabriel.delivery.api.model.input.GrupoInput;
import com.gabriel.delivery.domain.model.Grupo;
import com.gabriel.delivery.domain.repository.GrupoRepository;
import com.gabriel.delivery.domain.service.GrupoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
    private GrupoRepository grupoRepository;
    
    @Autowired
    private GrupoService service;
    
    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;
    
    @Autowired
    private GrupoDTODisassembler grupoInputDisassembler;
    
    @GetMapping
    public List<GrupoDTO> listar() {
        List<Grupo> todosGrupos = grupoRepository.findAll();
        
        return grupoDTOAssembler.toCollectionModel(todosGrupos);
    }
    
    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable Long grupoId) {
        Grupo grupo = service.buscarOuFalhar(grupoId);
        
        return grupoDTOAssembler.toModel(grupo);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
        
        grupo = service.salvar(grupo);
        
        return grupoDTOAssembler.toModel(grupo);
    }
    
    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable Long grupoId,
            @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = service.buscarOuFalhar(grupoId);
        
        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
        
        grupoAtual = service.salvar(grupoAtual);
        
        return grupoDTOAssembler.toModel(grupoAtual);
    }
    
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long grupoId) {
    	service.excluir(grupoId);	
    }   
	
}
