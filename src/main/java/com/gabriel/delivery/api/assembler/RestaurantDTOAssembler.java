package com.gabriel.delivery.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.gabriel.delivery.api.model.RestauranteDTO;
import com.gabriel.delivery.domain.model.Restaurante;

public class RestaurantDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes){
		return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
	}
	
	public RestauranteDTO toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteDTO.class);
	}

}
