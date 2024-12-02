package com.gabriel.delivery.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gabriel.delivery.api.model.EnderecoDTO;
import com.gabriel.delivery.api.model.RestauranteDTO;
import com.gabriel.delivery.domain.model.Endereco;
import com.gabriel.delivery.domain.model.Restaurante;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper =  new ModelMapper();
		
//		modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setTaxaFrete);
		
		var enderecoToEnderecoDTO = modelMapper.createTypeMap(
				Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoDTO.<String>addMapping(
				enderecoOrigem -> enderecoOrigem.getCidade().getEstado().getNome(),
				(enderecoDTODestino, value) -> enderecoDTODestino.getCidade().setEstado(value));
		
		return modelMapper;
	}
	
}
