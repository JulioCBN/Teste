package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.event.RecursoCriadoEvent;
import com.example.demo.models.Categoria;
import com.example.demo.services.CategoriaServices;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/categorias")
public class CategoriaController {

	@Autowired private CategoriaServices categoriaServices;
	@Autowired private ApplicationEventPublisher eventPublisher;
	
	@GetMapping()
	public List<Categoria> findAll() {
		return categoriaServices.findAll();
	}

	@GetMapping("/{codigo}")
	public Categoria findByAll(@PathVariable long codigo) {
		return categoriaServices.findById(codigo);
	}

	@GetMapping("/{codigo}/codigo")
	public ResponseEntity<?> buscarPorId(@PathVariable long codigo) {
		Categoria cat = categoriaServices.findById(codigo);
		return cat != null ? ResponseEntity.ok(cat) 
						   : ResponseEntity.notFound().build();  
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody @Valid Categoria categoria, HttpServletResponse httpServletResponse) {		
		Categoria cat = categoriaServices.cadastra(categoria);
		eventPublisher.publishEvent(new RecursoCriadoEvent(this, httpServletResponse, cat.getCodigo()));
		//teste.funcao(1111L, httpServletResponse);
		return  ResponseEntity.status(HttpStatus.CREATED).body(cat);
	}

	@PutMapping("/{codigo}")
	public Categoria atualizar(@PathVariable long codigo, @RequestBody @Valid Categoria categoria) {
		return categoriaServices.atualiza(codigo, categoria); 
	}
	
	@DeleteMapping("/{codigo}")
	public void excluir(@PathVariable long codigo) {
		categoriaServices.excluir(codigo);
	}
}
