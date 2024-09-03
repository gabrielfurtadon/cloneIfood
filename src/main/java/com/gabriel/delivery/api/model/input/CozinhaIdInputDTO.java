package com.gabriel.delivery.api.model.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CozinhaIdInputDTO {

	@NotNull
	private Long id;
	
}
