package com.gabriel.delivery.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.delivery.api.assembler.RestauranteDTOAssembler;
import com.gabriel.delivery.api.model.RestauranteDTO;
import com.gabriel.delivery.api.model.input.RestauranteInputDTO;
import com.gabriel.delivery.core.validation.ValidacaoException;
import com.gabriel.delivery.domain.exception.CozinhaNaoEncontradaException;
import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.model.Restaurante;
import com.gabriel.delivery.domain.repository.RestauranteRepository;
import com.gabriel.delivery.domain.service.RestauranteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private RestauranteService service;
	
	@Autowired
	private SmartValidator validator;
	
	@GetMapping
	public List<RestauranteDTO> listar() {
		return restauranteDTOAssembler.toCollectionModel(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public RestauranteDTO buscar(@PathVariable Long id){
		Restaurante restaurante = service.buscarOuException(id);
		
		return restauranteDTOAssembler.toModel(restaurante);
	}

	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInput) {
		try {
			Restaurante restaurante = toDomainObject(restauranteInput);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(restauranteDTOAssembler.toModel(service.salvar(restaurante)));  
		}catch(CozinhaNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid Restaurante restaurante){
		try {
			return ResponseEntity.ok().body(restauranteDTOAssembler.toModel(service.atualizar(id, restaurante)));
		}catch(CozinhaNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
		
		try {
			service.remover(id);
			return ResponseEntity.noContent() .build();
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
		
		Optional<Restaurante> orestauranteAtual = repository.findById(id);
		if(orestauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Restaurante restauranteAtual = orestauranteAtual.get();
		
		merge(campos, restauranteAtual);
		
		validate(restauranteAtual, "restaurante");
		
		return atualizar(id, restauranteAtual);
		
	}

	
	@GetMapping("/porTaxa")
	private List<Restaurante> porTaxaFrete( BigDecimal taxaMin, BigDecimal taxaMax) {
		return repository.findByTaxaFreteBetween(taxaMin, taxaMax);
	}
	
	@GetMapping("/porNome/{nome}")
	private List<Restaurante> porNomeEFreteGratis(@PathVariable String nome) {
		return repository.findComFreteGratis(nome);
	}
	
	private void validate(Restaurante restauranteAtual, String objectName) {
		BeanPropertyBindingResult bindingResults = new BeanPropertyBindingResult(restauranteAtual, objectName);
		validator.validate(restauranteAtual, null);
		
		if(bindingResults.hasErrors()) {
			throw new ValidacaoException(bindingResults);
		}
	}	

	@SuppressWarnings("deprecation")
	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino) {
		try {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		
		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
		Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
		field.setAccessible(true);
		
		Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
		
		ReflectionUtils.setField(field, restauranteDestino, novoValor);
		
		});
		}catch(IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause);
		}
	}
	
	
	private Restaurante toDomainObject(RestauranteInputDTO restauranteInputDTO){
		Restaurante restaurante = new Restaurante();
		Cozinha cozinha = new Cozinha();
		
		restaurante.setNome(restauranteInputDTO.getNome());
		restaurante.setTaxaFrete(restauranteInputDTO.getTaxaFrete());
		cozinha.setId(restauranteInputDTO.getCozinhaDto().getId());
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}
	
}
