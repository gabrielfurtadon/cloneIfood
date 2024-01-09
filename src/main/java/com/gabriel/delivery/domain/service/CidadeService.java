package com.gabriel.delivery.domain.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.delivery.domain.model.Cidade;
import com.gabriel.delivery.domain.model.Estado;
import com.gabriel.delivery.domain.repository.CidadeRepository;
import com.gabriel.delivery.domain.repository.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	CidadeRepository repository;
	
	@Autowired
	EstadoRepository estadoRepository;
	
	
	public Cidade salvar(Cidade cidade) {
		
		Long estadoId = cidade.getEstado().getId();
		Optional<Estado> oestado = estadoRepository.findById(estadoId);
		
		if(oestado.isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Não existem estado com o código %d associada à esse restaurante", estadoId));
		}
		
		Estado estado = oestado.get();
		cidade.setEstado(estado);
		
		return repository.save(cidade);
	}
	
	public Cidade atualizar(Long id, Cidade cidade) {
		
		Optional<Cidade> ocidade = repository.findById(id);
		
		if(ocidade.isPresent()) {
			Cidade cidadeFinal = ocidade.get();
			BeanUtils.copyProperties(cidade, cidadeFinal, "id");
			
			return this.salvar(cidadeFinal);
		}else {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cidade com o código %d ", id));
		}
		
	}
	
	public void excluir(Long id) {
		try {
		Optional<Cidade> ocidade = repository.findById(id);
		
		Cidade cidade = ocidade.get();
		repository.delete(cidade);
		}catch(NoSuchElementException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cidade com o código %d", id));
		}catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removido pois está em uso", id));
		}
		
	}
	
	
}
