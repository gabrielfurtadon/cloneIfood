package com.gabriel.delivery.notificacao;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.beans.factory.annotation.Qualifier;

@Retention(RetentionPolicy.RUNTIME) //ANOTAÇÃO SERÁ LIDA EM TEMPO DE EXECUÇÃO
@Qualifier
public @interface TipoDoNotificador {

	NivelUrgencia value(); //Permite que omita o value
	
}
