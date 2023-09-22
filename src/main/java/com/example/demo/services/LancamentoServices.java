package com.example.demo.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.PessoaServices;
import com.example.demo.exception.EntidadeNaoEncontradaException;
import com.example.demo.exception.PessoaInexistenteOuInativaException;
import com.example.demo.models.Lancamento;
import com.example.demo.models.Pessoa;
import com.example.demo.repositories.LancamentoRepository;
import com.example.demo.repositories.filter.LancamentoFilter;

import jakarta.transaction.Transactional;

@Service
public class LancamentoServices {

	@Autowired private LancamentoRepository lancamentoRepository;
	@Autowired private PessoaServices pessoaServices;
	@Autowired private CategoriaServices categoriaServices;

	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
		
	public Lancamento findById(Long codigo) {
		return lancamentoRepository.findById(codigo).get();
	}
	
	@Transactional
	public Lancamento cadastra(Lancamento lancamento) {
		categoriaServices.buscarOuFalhar(lancamento.getCategoria().getCodigo());
		Pessoa pessoa = pessoaServices.buscarOuFalhar(lancamento.getPessoa().getCodigo());
		if (pessoa.isInativo())
			throw new PessoaInexistenteOuInativaException();
		return lancamentoRepository.save(lancamento);
	}
	
	@Transactional
	public Lancamento atualiza(long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalva = buscarOuFalhar(codigo);
		BeanUtils.copyProperties(lancamento, lancamentoSalva, "codigo");
		return lancamentoRepository.save(lancamentoSalva);
	}	
	
	@Transactional
	public void excluir(long codigo) {
		if (buscarOuFalhar(codigo) == null) return;
		
		try {
			lancamentoRepository.deleteById(codigo);	
			lancamentoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException("Lancamento", codigo);    
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException();
		}
	}
	
	public Lancamento buscarOuFalhar(Long codigo) {
		return lancamentoRepository.findById(codigo)
				.orElseThrow(()-> new  EntidadeNaoEncontradaException("Lancamento", codigo));
	}
}
