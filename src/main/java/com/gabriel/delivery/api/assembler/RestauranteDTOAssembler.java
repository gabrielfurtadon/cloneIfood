package com.gabriel.delivery.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gabriel.delivery.api.model.CozinhaDTO;
import com.gabriel.delivery.api.model.RestauranteDTO;
import com.gabriel.delivery.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler {

	public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes){
		return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
	}
	
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restDTO = new RestauranteDTO();
		CozinhaDTO cozinhaDTO = new CozinhaDTO();
		cozinhaDTO.setId(restaurante.getCozinha().getId());
		cozinhaDTO.setNome(restaurante.getCozinha().getNome());
		
		restDTO.setId(restaurante.getId());
		restDTO.setNome(restaurante.getNome());
		restDTO.setTaxaFrete(restaurante.getTaxaFrete());
		restDTO.setCozinhaDto(cozinhaDTO);
		return restDTO;
	}
	
}
