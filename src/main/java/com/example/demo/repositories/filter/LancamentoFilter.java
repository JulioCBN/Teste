package com.example.demo.repositories.filter;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LancamentoFilter {
	private String descricao;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoDe;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataVencimentoAte;
}
