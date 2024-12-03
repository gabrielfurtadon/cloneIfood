package com.gabriel.delivery.domain.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gabriel.delivery.domain.exception.CidadeNaoEncontradaException;
import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.RestauranteNaoEncontradoException;
import com.gabriel.delivery.domain.model.Cidade;
import com.gabriel.delivery.domain.model.Estado;
import com.gabriel.delivery.domain.repository.CidadeRepository;
import com.gabriel.delivery.domain.repository.EstadoRepository;

import jakarta.transaction.Transactional;

@Service
public class CidadeService {
	
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe cadastro de cidade com o código %d";

	@Autowired
	CidadeRepository repository;
	
	@Autowired
	EstadoRepository estadoRepository;
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.findById(estadoId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(String.format("Não existem estado com o código %d associada à esse restaurante", estadoId)));		
		

		cidade.setEstado(estado);
		
		return repository.save(cidade);
	}
	
	@Transactional
	public Cidade atualizar(Long id, Cidade cidade) {
		
		Optional<Cidade> ocidade = repository.findById(id);
		
		if(ocidade.isPresent()) {
			Cidade cidadeFinal = ocidade.get();
			BeanUtils.copyProperties(cidade, cidadeFinal, "id");
			
			return this.salvar(cidadeFinal);
		}else {
			throw new CidadeNaoEncontradaException(String.format("Não existe cidade com o código %d ", id));
		}
		
	}
	
	@Transactional
	public void excluir(Long id) {
		try {
			repository.deleteById(id);
			repository.flush();
		
		}catch(NoSuchElementException e) {
			throw new CidadeNaoEncontradaException(String.format("Não existe cadastro de cidade com o código %d", id));
		}catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removido pois está em uso", id));
		}
		
	}
	
	public Cidade buscarOuException(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new CidadeNaoEncontradaException(String.
						format(MSG_CIDADE_NAO_ENCONTRADA, id)));
	}
	
	
}
