package com.gabriel.delivery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.gabriel.delivery.modelo.Cliente;
import com.gabriel.delivery.notificacao.NivelUrgencia;
import com.gabriel.delivery.notificacao.Notificador;
import com.gabriel.delivery.notificacao.TipoDoNotificador;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class AtivacaoClienteService {
	
	private static final String MSG = "Seu cadastro no sistema está ativado!";
	
	@TipoDoNotificador(NivelUrgencia.NORMAL)
	@Autowired
	Notificador notificador;
	
	@PostConstruct
	public void init() {
		System.out.println("INIT");
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("DESTROY");
	}
	
	public void ativar(Cliente cliente) {
		cliente.ativar();
		
			notificador.notificar(cliente, MSG);
		
		
	}
	

}
