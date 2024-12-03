package com.gabriel.delivery.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.delivery.api.model.input.RestauranteInputDTO;
import com.gabriel.delivery.domain.model.Cidade;
import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
    public Restaurante toDomainObject(RestauranteInputDTO restauranteInput) {
       return modelMapper.map(restauranteInput, Restaurante.class);
    }
    
    public void copyToDomainObject(RestauranteInputDTO restaurantInput, Restaurante restaurante) {
    	restaurante.setCozinha(new Cozinha());
    	
    	if(restaurante.getEndereco() != null) {
    		restaurante.getEndereco().setCidade(new Cidade());
    	}
    	
    	modelMapper.map(restaurantInput, restaurante);
    }
}
