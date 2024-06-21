package com.gabriel.delivery.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	MESSAGE_NOT_READABLE("/message-not-readable", "Message not readable"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}
	
}
