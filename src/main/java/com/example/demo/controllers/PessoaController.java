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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.PessoaServices;
import com.example.demo.event.RecursoCriadoEvent;
import com.example.demo.models.Pessoa;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/pessoas")
public class PessoaController {

	@Autowired private PessoaServices pessoaServices;
	@Autowired private ApplicationEventPublisher publisher;
	
	@GetMapping()
	public List<Pessoa> findAll() {
		return pessoaServices.findAll();
	}

	@GetMapping("/{codigo}")
	public Pessoa findByAll(@PathVariable long codigo) {
		return pessoaServices.findById(codigo);
	}

	@GetMapping("/{codigo}/id")
	public ResponseEntity<?> buscarPorId(@PathVariable long codigo) {
		Pessoa pes = pessoaServices.findById(codigo);
		return pes != null ? ResponseEntity.ok(pes) 
						   : ResponseEntity.notFound().build();  
	}
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response) {		
		Pessoa pes = pessoaServices.cadastra(pessoa);		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pes.getCodigo()));		
		return  ResponseEntity.status(HttpStatus.CREATED).body(pes);
	}

	@PutMapping("/{codigo}")
	public Pessoa atualizar(@PathVariable long codigo, @RequestBody @Valid Pessoa pessoa) {
		return pessoaServices.atualiza(codigo, pessoa); 
	}
	
	@PutMapping("/{codigo}/ativo")
	public Pessoa atualizaPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		return pessoaServices.atualizaPropriedadeAtivo(codigo, ativo);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable long codigo) {
		pessoaServices.excluir(codigo);
	}
}
