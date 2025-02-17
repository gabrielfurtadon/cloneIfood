package com.gabriel.delivery.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.delivery.api.model.input.GrupoInput;
import com.gabriel.delivery.domain.model.Grupo;

@Component
public class GrupoDTODisassembler {

	@Autowired
    private ModelMapper modelMapper;
    
    public Grupo toDomainObject(GrupoInput grupoInput) {
        return modelMapper.map(grupoInput, Grupo.class);
    }
    
    public void copyToDomainObject(GrupoInput grupoInput, Grupo grupo) {
        modelMapper.map(grupoInput, grupo);
    }   
	
}
