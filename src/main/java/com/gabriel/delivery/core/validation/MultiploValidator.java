package com.gabriel.delivery.core.validation;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

	private int numeroMultiplo;
	
	@Override
	public void initialize(Multiplo constraintAnnotation) { //Inicializa o validador pra preparar para as chamadas futuras do isValid
		this.numeroMultiplo = constraintAnnotation.numero(); //PEGA O NUMERO PASSADO COMO PARAMETRO NO USO DA ANOTACÃO
	}
	
	@Override
	public boolean isValid(Number value, ConstraintValidatorContext context) { //Lógica de validacão
		boolean valido = true;
		
		if(value != null) {
			var valorDecimal = BigDecimal.valueOf(value.doubleValue());
			var multiploDecimal = BigDecimal.valueOf(this.numeroMultiplo);
			var resto = valorDecimal.remainder(multiploDecimal); //.remainder = pega o resto do bigDecimal
			
			valido = BigDecimal.ZERO.compareTo(resto) == 0	;
		}
		return valido;
	}

}
