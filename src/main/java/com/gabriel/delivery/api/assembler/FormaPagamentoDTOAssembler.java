package com.gabriel.delivery.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.delivery.api.model.input.FormaPagamentoDTO;
import com.gabriel.delivery.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler {


    @Autowired
    private ModelMapper modelMapper;
    
    public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }
    
    public List<FormaPagamentoDTO> toCollectionModel(Collection<FormaPagamento> formasPagamentos) {
        return formasPagamentos.stream()
                .map(formaPagamento -> toModel(formaPagamento))
                .collect(Collectors.toList());
    }
    
	
}
