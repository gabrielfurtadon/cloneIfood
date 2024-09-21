package com.gabriel.delivery.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.delivery.api.model.input.CozinhaInputDTO;
import com.gabriel.delivery.domain.model.Cozinha;

@Component
public class CozinhaInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;
    
    public Cozinha toDomainObject(CozinhaInputDTO cozinhaInput) {
        return modelMapper.map(cozinhaInput, Cozinha.class);
    }
    
    public void copyToDomainObject(CozinhaInputDTO cozinhaInput, Cozinha cozinha) {
        modelMapper.map(cozinhaInput, cozinha);
    }
    
}