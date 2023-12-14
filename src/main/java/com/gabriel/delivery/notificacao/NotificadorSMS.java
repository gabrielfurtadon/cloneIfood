package com.gabriel.delivery.notificacao;

import org.springframework.stereotype.Component;

import com.gabriel.delivery.modelo.Cliente;

@TipoDoNotificador(NivelUrgencia.URGENTE)
@Component
public class NotificadorSMS implements Notificador {


	@Override
	public void notificar(Cliente cliente, String mensagem) {
		
		System.out.printf("Notificando %s através do SMS %s : %s\n", cliente.getNome(), cliente.getTelefone(), mensagem);
	}


	
	
}
