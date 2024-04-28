package com.gabriel.delivery.domain.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@JsonRootName("cozinha")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@JsonIgnore //PARA EVITAR LOOP INFINITO
	@OneToMany(mappedBy = "cozinha") //DIZENDO QUAL O NOME DA PROPRIEDADE EM RESTAURANTE QUE FOI USADA PARA FAZER O RELACIONAMENTO
	private List<Restaurante> restaurante = new ArrayList<>();

}
