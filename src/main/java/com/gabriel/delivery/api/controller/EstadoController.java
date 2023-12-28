package com.gabriel.delivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabriel.delivery.domain.model.Estado;
import com.gabriel.delivery.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	EstadoRepository estadoRepository;
	
	@GetMapping("/")
	public List<Estado> listar() {
		return estadoRepository.findAll();
		}
	
}
