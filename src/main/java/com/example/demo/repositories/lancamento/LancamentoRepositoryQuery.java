package com.example.demo.repositories.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.models.Lancamento;
import com.example.demo.repositories.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	public Page<Lancamento> filtrar(LancamentoFilter filter, Pageable pageable);
}
