package com.gabriel.delivery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gabriel.delivery.notificacao.NotificadorEmail;

@Configuration
public class ApplicationConfig {

	//METODO RESPONSAVEL POR INSTANCIAR E CONFIGURAR UM NOTIFICADOREMAIL
//	@Bean
//	public NotificadorEmail notificadorEmail() {
//		NotificadorEmail notificador = new NotificadorEmail("smtp.teste");
//		notificador.setCaixaAlta(true);
//		
//		return notificador;
//	}
}
