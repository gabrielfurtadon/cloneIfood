package com.gabriel.delivery.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.delivery.api.model.input.RestauranteInputDTO;
import com.gabriel.delivery.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
    public Restaurante toDomainObject(RestauranteInputDTO restauranteInput) {
       return modelMapper.map(restauranteInput, Restaurante.class);
    }
}
