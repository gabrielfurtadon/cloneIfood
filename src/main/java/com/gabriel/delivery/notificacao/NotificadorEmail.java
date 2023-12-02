package com.gabriel.delivery.notificacao;

import org.springframework.stereotype.Component;

import com.gabriel.delivery.modelo.Cliente;

@Component
public class NotificadorEmail implements Notificador {

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		
		System.out.printf("Notificando %s através do email %s : %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
	}

	
	
}
