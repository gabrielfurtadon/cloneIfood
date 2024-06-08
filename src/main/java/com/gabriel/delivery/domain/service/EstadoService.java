package com.gabriel.delivery.domain.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.EstadoNaoEncontradoException;
import com.gabriel.delivery.domain.model.Estado;
import com.gabriel.delivery.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	EstadoRepository repository;
	
	
	public Estado salvar(Estado estado) {
		return repository.save(estado);
	}
	
	public Estado atualizar(Long id, Estado estado) {
		
		Optional<Estado> oestado = repository.findById(id);
		
		if(oestado.isPresent()) {
			Estado estadoFinal = oestado.get();
			BeanUtils.copyProperties(estado, estadoFinal, "id");
			
			return repository.saveAndFlush(estadoFinal);
		}else {
			throw new EstadoNaoEncontradoException(String.format("Não existe estado com o código %d ", id));
		}
		
	}
	
	public void excluir(Long id) {
		try {
		Optional<Estado> oestado = repository.findById(id);
		
		Estado estado = oestado.get();
		repository.delete(estado);
		}catch(NoSuchElementException e) {
			throw new EstadoNaoEncontradoException(String.format("Não existe cadastro de Estado com o código %d", id));
		}catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removido pois está em uso", id));
		}
		
	}
	
	
}
