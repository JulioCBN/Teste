package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Size(min = 2, max = 50)
	@NotBlank
	private String nome;
	
	@NotNull
	private Boolean ativo;
	
	@Embedded
	private Endereco endereco;
	
	@JsonIgnore
	@Transient
	public Boolean isInativo() {
		return !this.ativo;
	}
}
