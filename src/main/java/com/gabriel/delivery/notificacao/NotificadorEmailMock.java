package com.gabriel.delivery.notificacao;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.gabriel.delivery.modelo.Cliente;

@Profile(value = "tu")
@TipoDoNotificador(NivelUrgencia.NORMAL)
@Component
public class NotificadorEmailMock implements Notificador {

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		
		System.out.printf("MOCK : Notificando %s através do email %s : %s\n", cliente.getNome(), cliente.getEmail(), mensagem);
	}

	
	
} 
