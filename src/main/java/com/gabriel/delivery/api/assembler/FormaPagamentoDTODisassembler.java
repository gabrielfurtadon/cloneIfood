package com.gabriel.delivery.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.delivery.api.model.input.FormaPagamentoInputDTO;
import com.gabriel.delivery.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTODisassembler {

	 @Autowired
	    private ModelMapper modelMapper;
	    
	    public FormaPagamento toDomainObject(FormaPagamentoInputDTO formaPagamentoInput) {
	        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
	    }
	    
	    public void copyToDomainObject(FormaPagamentoInputDTO formaPagamentoInput, FormaPagamento formaPagamento) {
	        modelMapper.map(formaPagamentoInput, formaPagamento);
	    }   
	
}
