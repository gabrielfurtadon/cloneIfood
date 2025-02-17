package com.gabriel.delivery.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gabriel.delivery.domain.exception.NegocioException;
import com.gabriel.delivery.domain.exception.UsuarioNaoEncontradoException;
import com.gabriel.delivery.domain.model.Usuario;
import com.gabriel.delivery.domain.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	 @Autowired
	    private UsuarioRepository usuarioRepository;
	    
	    @Transactional
	    public Usuario salvar(Usuario usuario) {
	    	
	    	Optional<Usuario> existsUser = usuarioRepository.findByEmail(usuario.getEmail());
	    	
	    	if(existsUser.isPresent()) {
	    		throw new NegocioException(String.format("Já existe usuário com o email cadastrado : %s ",usuario.getEmail()));
	    	}
	    	
	        return usuarioRepository.save(usuario);
	    }
	    
	    @Transactional
	    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
	        Usuario usuario = buscarOuFalhar(usuarioId);
	        
	        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
	            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
	        }
	        
	        usuario.setSenha(novaSenha);
	    }

	    public Usuario buscarOuFalhar(Long usuarioId) {
	        return usuarioRepository.findById(usuarioId)
	            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
	    }            
	
}
