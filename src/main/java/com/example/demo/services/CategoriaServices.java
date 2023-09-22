package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.EntidadeNaoEncontradaException;
import com.example.demo.models.Categoria;
import com.example.demo.models.Pessoa;
import com.example.demo.repositories.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaServices {

	@Autowired private CategoriaRepository categoriaRepository;
	
	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}
		
	public Categoria findById(long id) {
		return categoriaRepository.findById(id).get();
	}
	
	@Transactional
	public Categoria cadastra(Categoria cat) {
		return categoriaRepository.save(cat);
	}
	
	@Transactional
	public Categoria atualiza(long id, Categoria cat) {
		Categoria categoria = findById(id);
		return categoriaRepository.save(categoria);
	}
	
	@Transactional
	public void excluir(long id) {
		categoriaRepository.deleteById(id);
	}
	
	public Categoria buscarOuFalhar(Long codigo) {
		return categoriaRepository.findById(codigo)
				.orElseThrow(()-> new  EntidadeNaoEncontradaException("Categoria", codigo));
	}	
}
