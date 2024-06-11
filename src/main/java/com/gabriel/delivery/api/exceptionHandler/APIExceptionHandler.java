package com.gabriel.delivery.api.exceptionHandler;


import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.weaver.tools.Traceable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gabriel.delivery.domain.exception.EntidadeEmUsoException;
import com.gabriel.delivery.domain.exception.EntidadeNaoEncontradaException;
import com.gabriel.delivery.domain.exception.NegocioException;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler{


	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if(rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
		}
		
		HttpStatus  statusCode = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
		String detail = "The body of the requisition is invalid. Verify the sintax error";
		
		Problem problem = createProblemBuilder(statusCode, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
	HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		String path = ex.getPath().stream()
				.map(ref -> ref.getFieldName())
				.collect(Collectors.joining("."));
		
		ProblemType problemType = ProblemType.MESSAGE_NOT_READABLE;
		String detail = String.format("The field '%s'has recieved value '%s', which is an invalid type."
				+ "Please, insert some '%s'type." , path, ex.getValue(), ex.getTargetType().getSimpleName());
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		String detail = ex.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
		@ExceptionHandler(EntidadeEmUsoException.class)
		public ResponseEntity<?> handleEntidadeEmUsoException(
		        EntidadeEmUsoException ex, WebRequest request) {
		    
		    HttpStatus status = HttpStatus.CONFLICT;
		    ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		    String detail = ex.getMessage();
		    
		    Problem problem = createProblemBuilder(status, problemType, detail).build();
		    
		    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		}

		
		@ExceptionHandler(NegocioException.class)
		public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request) {

		    HttpStatus status = HttpStatus.BAD_REQUEST;
		    ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		    String detail = ex.getMessage();
		    
		    Problem problem = createProblemBuilder(status, problemType, detail).build();
		    
		    return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		}
	
		
		@Override
		protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
				HttpStatusCode status, WebRequest request) {
			
			if (body == null) {
				body = Problem.builder()
					.title(((HttpStatus) status).getReasonPhrase())
					.status(status.value())
					.build();
			} else if (body instanceof String) {
				body = Problem.builder()
					.title((String) body)
					.status(status.value())
					.build();
			}
			
			return super.handleExceptionInternal(ex, body, headers, status, request);
		}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	        HttpHeaders headers, HttpStatusCode status, WebRequest request) {

	    HttpStatus statusCode = (HttpStatus) status;
	   
	    Problem problem = createProblemBuilder(statusCode,
				ProblemType.DADOS_INVALIDOS,
				ex.getMessage()).build();
	    
	    return handleExceptionInternal(ex, problem, headers, status, request);
	} 
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatusCode status,
			ProblemType problemType, String detail) {
		
		return Problem.builder()
			.status(status.value())
			.type(problemType.getUri())
			.title(problemType.getTitle())
			.detail(detail);
	}
	
}
