package com.gabriel.delivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.delivery.api.assembler.FormaPagamentoDTOAssembler;
import com.gabriel.delivery.api.model.input.FormaPagamentoDTO;
import com.gabriel.delivery.domain.model.Restaurante;
import com.gabriel.delivery.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {
	
	@Autowired
	private RestauranteService service;
	
	@Autowired
	private FormaPagamentoDTOAssembler assembler;
	

	//Retornar formas de pagamento de um restaurante
	@GetMapping
	public List<FormaPagamentoDTO> listar(@PathVariable Long restauranteId ) {
		Restaurante restaurante = service.buscarOuException(restauranteId);
		
		return assembler.toCollectionModel(restaurante.getFormasPagamento());
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		service.removerFormaPagamento(restauranteId, formaPagamentoId);
	}
	
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		service.adicionarFormaPagamento(restauranteId, formaPagamentoId);
	}
	

	

}
