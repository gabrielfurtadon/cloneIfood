package com.gabriel.delivery.core.validation;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object>{
	
	private String valueField;
	private String descriptionField;
	private String mandatoryDescription;
	
	@Override
	public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
		this.valueField = constraintAnnotation.valueField();
		this.descriptionField = constraintAnnotation.descriptionField();
		this.mandatoryDescription = constraintAnnotation.mandatoryDescription();
	}
	
	
	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		boolean valid = true;
		//PEGANDO O VALOR DO GET DO ATRIBUTO QUE ESTAMOS QUERENDO VALIDAR (NO CASO DO RESTARUANTE, TAXAFRETE e nome
		try {
			BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(object.getClass(), valueField).getReadMethod().invoke(object);
			
			String description = (String) BeanUtils.getPropertyDescriptor(object.getClass(), descriptionField).getReadMethod().invoke(object);
			
			if(value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
				valid = description.toLowerCase().contains(this.mandatoryDescription.toLowerCase()); //SE A DESCRICAO TIVER A DESCRICAO OBRIGATÓRIA, RETORNA TRUE
			}
			
			return valid;
		} catch (Exception e) {
			throw new ValidationException(e);
		} 
	}

}
