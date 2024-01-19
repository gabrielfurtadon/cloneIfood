package com.gabriel.delivery.domain.exception;

public class EstadoNaoEncontrado extends EntidadeNaoEncontradaException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public EstadoNaoEncontrado(String msg) {
		super(msg);
	}
	
	public EstadoNaoEncontrado(Long id) { // passa EstadoNaoEncontrado(id)
		this(String.format("Não existe cadastro de estado com o código %d", id));
	}
	
}
