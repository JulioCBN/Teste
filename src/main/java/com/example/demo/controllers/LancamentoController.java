package com.example.demo.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.event.RecursoCriadoEvent;
import com.example.demo.exception.PessoaInexistenteOuInativaException;
import com.example.demo.exceptionhandler.AlgaMonayExceptionHandler.Erro;
import com.example.demo.models.Lancamento;
import com.example.demo.repositories.filter.LancamentoFilter;
import com.example.demo.services.LancamentoServices;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/lancamentos")
public class LancamentoController {

	@Autowired private MessageSource messageSource;
	@Autowired private LancamentoServices lancamentoServices;
	@Autowired private ApplicationEventPublisher eventPublisher;
	
	@GetMapping()
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		return lancamentoServices.filtrar(lancamentoFilter, pageable);
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<?> buscarPorId(@PathVariable long codigo) {
		Lancamento lan = lancamentoServices.findById(codigo);
		return lan != null ? ResponseEntity.ok(lan) 
						   : ResponseEntity.notFound().build();  
	}
	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody @Valid Lancamento lancamento, HttpServletResponse httpServletResponse) {				
		Lancamento lan = lancamentoServices.cadastra(lancamento);
		eventPublisher.publishEvent(new RecursoCriadoEvent(this, httpServletResponse, lan.getCodigo()));
		//teste.funcao(1111L, httpServletResponse);
		return  ResponseEntity.status(HttpStatus.CREATED).body(lan);
	}

	@PutMapping("/{codigo}")
	public Lancamento atualizar(@PathVariable long codigo, @RequestBody @Valid Lancamento lancamento) {
		return lancamentoServices.atualiza(codigo, lancamento); 
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable long codigo) {
		lancamentoServices.excluir(codigo);
	}
	
	@ExceptionHandler(PessoaInexistenteOuInativaException.class)
	public ResponseEntity<?> handlerPessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		System.out.println("h.");
		String msgUsu = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String msgDev = ex.toString();		
		List<Erro> erros = Arrays.asList(new Erro(msgUsu, msgDev));				
		return ResponseEntity.badRequest().body(erros);
	}
}
