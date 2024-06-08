package com.gabriel.delivery.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public EstadoNaoEncontradoException(String msg) {
		super(msg);
	}
	
	public EstadoNaoEncontradoException(Long id) { // passa EstadoNaoEncontrado(id)
		this(String.format("Não existe cadastro de estado com o código %d", id));
	}
	
}
