package com.gabriel.delivery.notificacao;

import com.gabriel.delivery.modelo.Cliente;

public interface Notificador {

	void notificar(Cliente cliente, String mensagem);

}