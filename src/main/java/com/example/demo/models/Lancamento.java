package com.example.demo.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "lancamento")
public class Lancamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@NotNull
	@Size(min= 3, max=50)
	private String descricao;
		
	@NotNull
	@NotNull
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;
	
	@Column(name = "data_pagamento")
	private LocalDate dataPagamento;

	@NotNull
	private BigDecimal valor;
	
	@Size(min= 3, max=100)
	private String observacao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TipoLancamento tipo;
	
	@NotNull	
	@ManyToOne
	@JoinColumn(name = "codigo_categoria")
	private Categoria categoria;
	
	@NotNull	
	@ManyToOne
	@JoinColumn(name = "codigo_pessoa")
	private Pessoa pessoa;
	
}
