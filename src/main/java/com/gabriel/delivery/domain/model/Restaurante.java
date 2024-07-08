package com.gabriel.delivery.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabriel.delivery.core.validation.Groups;
import com.gabriel.delivery.core.validation.TaxFrete;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotNull 
	//@PositiveOrZero
	@TaxFrete
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	//@JsonIgnoreProperties("hibernateLazyInitializer")
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@NotNull
	@ManyToOne //(fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id") //DONO DA ASSOCIACAO (SO VAI TER A COLUNA NA TABELA RESTAURANTE)
	private Cozinha cozinha;
	
	@JsonIgnore
	@Embedded //Indica que essa proprieadade e de um tipo embedabble (esta sendo incorporada)
	private Endereco endereco;
	
	@JsonIgnore
	@CreationTimestamp //ATRIBUI A DATA/HORA ATUAL DO MOMENTO QUE FOR SALVA PELA PRIMEIRA VEZ
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime  dataCadastro;//representa data hora sem fusohorario
	
	@JsonIgnore
	@UpdateTimestamp //DATA HORA/HORA ATUAL SEMPRE QUE A ENTIDADE FOR ATUALIZADA
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime  dataAtualizacao;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento", joinColumns = @JoinColumn(name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")) //NOME DA TABELA INTERMEDIARIA QUE SERA CRIADA
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>(); 
	
}
