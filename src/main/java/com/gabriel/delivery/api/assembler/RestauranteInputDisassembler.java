package com.gabriel.delivery.api.assembler;

import org.springframework.stereotype.Component;

import com.gabriel.delivery.api.model.input.RestauranteInputDTO;
import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {

    public Restaurante toDomainObject(RestauranteInputDTO restauranteInput) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNome(restauranteInput.getNome());
        restaurante.setTaxaFrete(restauranteInput.getTaxaFrete());
        
        Cozinha cozinha = new Cozinha();
        cozinha.setId(restauranteInput.getCozinhaDto().getId());
        
        restaurante.setCozinha(cozinha);
        
        return restaurante;
    }
    
}
