package com.gabriel.delivery.core.validation;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class ValidationConfig {

	//Metodo para especificar o uso do message.properties ao inves do validationMessages.properties como bean validation
	@Bean
	public LocalValidatorFactoryBean  validator(MessageSource messageSource) {  //integracao e configuracao do bean validation com o Spring
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource); //Indica que o validationMessage Source é o message Source
		return bean;
	}
	
}
