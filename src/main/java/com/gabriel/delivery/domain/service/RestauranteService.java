package com.gabriel.delivery.domain.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.gabriel.delivery.api.assembler.RestauranteInputDisassembler;
import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.RestauranteNaoEncontradoException;
import com.gabriel.delivery.domain.model.Cidade;
import com.gabriel.delivery.domain.model.Cozinha;
import com.gabriel.delivery.domain.model.FormaPagamento;
import com.gabriel.delivery.domain.model.Restaurante;
import com.gabriel.delivery.domain.repository.CozinhaRepository;
import com.gabriel.delivery.domain.repository.RestauranteRepository;

import jakarta.transaction.Transactional;

@Service
public class RestauranteService {

	private static final String MSG_RESTAURANTE_NAO_ENCONTRADA = "Não existe cadastro de restaurante com o código %d";
	
	@Autowired
	RestauranteInputDisassembler restauranteInputDisassembler;
	
	@Autowired
	RestauranteRepository repository;
	
	@Autowired 
	CozinhaRepository cozinhaRepository;
	
	@Autowired
	CozinhaService cozinhaService;
	
	@Autowired
	CidadeService cidadeService;
	
	@Autowired
	FormaPagamentoService formaPagamentoService;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
//		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
//				.orElseThrow(() -> new RestauranteNaoEncontradoException(String.format("Não existem cozinha com o código %d associada à esse restaurante", cozinhaId)));
		Cozinha cozinha = cozinhaService.buscarOuException(cozinhaId);
		Cidade cidade = cidadeService.buscarOuException(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
		
		return repository.save(restaurante);
	}
	
	@Transactional
	public Restaurante atualizar(Long id, Restaurante restaurante){
			
			Optional<Restaurante> restauranteAtual = repository.findById(id);
			
			if(restauranteAtual.isPresent()) {
				Restaurante restauranteFinal = restauranteAtual.get();
				//restauranteInputDisassembler.copyToDomainObject(null, restaurante);
				BeanUtils.copyProperties(restaurante, restauranteFinal, "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
				
				return this.salvar(restauranteFinal);
				
			}else {
				throw new RestauranteNaoEncontradoException(String.format("Não existem restaurante com o código %d ", id));
			}
			
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			
			repository.deleteById(id);
			repository.flush();
		}catch(NoSuchElementException e) {
			throw new RestauranteNaoEncontradoException(String.format("Não existe cadastro de restaurante com o código %d", id));
		}catch (DataIntegrityViolationException error) {
			throw new EntidadeEmUsoException(String.format("restaurante de código %d não pode ser removida pois está em uso", id));
		}
	}
	
	@Transactional
	public void ativar(Long Id) {
		Restaurante restauranteAtual = buscarOuException(Id);
		
		restauranteAtual.ativar();;
	}
	
	@Transactional
	public void inativar(Long Id) {
		Restaurante restauranteAtual = buscarOuException(Id);
		
		restauranteAtual.inativar();;
	}
	
	@Transactional 
	public void removerFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuException(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	@Transactional 
	public void adicionarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuException(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	
	public Restaurante buscarOuException(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new RestauranteNaoEncontradoException(String.
						format(MSG_RESTAURANTE_NAO_ENCONTRADA, id)));
	}
	
}
