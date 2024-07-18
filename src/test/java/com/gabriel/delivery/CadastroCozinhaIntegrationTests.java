package com.gabriel.delivery;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.service.CozinhaService;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	CozinhaService cozinhaService;
	
	@Test
	public void testarCadastroCozinhaComSucesso() {
		//cenario
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Japonese");
		//acão
		novaCozinha = cozinhaService.adicionar(novaCozinha);
		//validacão
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}
	
	@Test
	public void testarCadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);
		ConstraintViolationException erroEsperado =
			      Assertions.assertThrows(ConstraintViolationException.class, () -> {
			    	  cozinhaService.adicionar(novaCozinha);
			      });
			   
			   assertThat(erroEsperado).isNotNull();
	}

}
