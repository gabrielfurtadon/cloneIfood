package com.gabriel.delivery.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.delivery.api.model.input.EstadoInputDTO;
import com.gabriel.delivery.domain.model.Estado;

@Component
public class EstadoInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Estado toDomainObject(EstadoInputDTO estadoInput) {
        return modelMapper.map(estadoInput, Estado.class);
    }
    
    public void copyToDomainObject(EstadoInputDTO estadoInput, Estado estado) {
        modelMapper.map(estadoInput, estado);
    }   
} 
