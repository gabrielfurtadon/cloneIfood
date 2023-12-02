package com.gabriel.delivery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gabriel.delivery.modelo.Cliente;
import com.gabriel.delivery.notificacao.Notificador;

@Component
public class AtivacaoClienteService {
	
	private static final String MSG = "Seu cadastro no sistema está ativado!";
	
	@Autowired
	private List<Notificador> notificadores;
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		for(Notificador notificador : notificadores) {
			notificador.notificar(cliente, MSG);
		}
		
	}
	

}
