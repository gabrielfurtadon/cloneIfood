package com.gabriel.delivery.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE})//SÓ PODE SER USADA EM CLASSE, INTERFACE, ETC
@Retention(RUNTIME)
@Constraint(validatedBy = {ValorZeroIncluiDescricaoValidator.class })
public @interface ValorZeroIncluiDescricao {

	String message() default "Descricao obrigatória inválida";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	
	String valueField();
	String descriptionField();
	String mandatoryDescription();
	
	
}
