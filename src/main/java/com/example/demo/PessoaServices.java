package com.example.demo;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.exception.EntidadeNaoEncontradaException;
import com.example.demo.models.Pessoa;
import com.example.demo.repositories.PessoaRepository;

import jakarta.transaction.Transactional;

@Service
public class PessoaServices {

	@Autowired private PessoaRepository pessoaRepository;
	
	public List<Pessoa> findAll() {
		return pessoaRepository.findAll();
	}
		
	public Pessoa findById(long codigo) {
		return pessoaRepository.findById(codigo).get();
	}
	
	@Transactional
	public Pessoa cadastra(Pessoa pessoa) {
		return pessoaRepository.save(pessoa);
	}
	
	@Transactional
	public Pessoa atualiza(long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarOuFalhar(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return pessoaRepository.save(pessoaSalva);
	}
	
	@Transactional
	public Pessoa atualizaPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalvar = buscarOuFalhar(codigo);
		pessoaSalvar.setAtivo(ativo);
		return pessoaRepository.save(pessoaSalvar);		
	}
	
	@Transactional
	public void excluir(long codigo) {
		if (buscarOuFalhar(codigo) == null) return;
		
		try {
			pessoaRepository.deleteById(codigo);	
			pessoaRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new RuntimeException();
			//throw new EntidadeNaoEncontradaException("Cidade", cidadeId);    
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException();
		}
		
		
		//Pessoa pessoa = pessoaRepository.findById(codigo).get();
		//pessoaRepository.delete(pessoa);
	}
	
	public Pessoa buscarOuFalhar(Long codigo) {
		return pessoaRepository.findById(codigo)
				.orElseThrow(()-> new  EntidadeNaoEncontradaException("Pessoa", codigo));
	}
}
