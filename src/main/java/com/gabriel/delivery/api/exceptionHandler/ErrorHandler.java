package com.gabriel.delivery.api.exceptionHandler;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorHandler {

	private LocalDateTime dataHora;
	private String msg;
	
	
}
