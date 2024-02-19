package com.gabriel.delivery.api.exceptionHandler;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;

public class APIExceptionHandler extends ResponseEntityExceptionHandler{


	@ExceptionHandler(EntidadeNaoEncontradaException.class) 
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request)	{
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		ErrorHandler error = createProblemBuilder(status,
				ProblemType.ENTIDADE_NAO_ENCONTRADA,
				ex.getMessage()).build();
		
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		
		if(body == null) {
			body = ErrorHandler.builder()
					.title(ex.getMessage())
					.status(statusCode.value())
					.build();
		}
		if(body instanceof String ) {
			body = ErrorHandler.builder()
					.title(ex.getMessage())
					.status(statusCode.value())
					.build();
		}
		
		
		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}
	
	private ErrorHandler.ErrorHandlerBuilder createProblemBuilder(HttpStatus status, ProblemType type, String detail){
		
		return ErrorHandler.builder()
				.status(status.value())
				.type(type.getUri())
				.title(type.getTitle())
				.detail(detail);
		
	}
	
}
