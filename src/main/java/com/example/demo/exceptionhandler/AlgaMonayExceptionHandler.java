package com.example.demo.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.exception.EntidadeNaoEncontradaException;

@ControllerAdvice
public class AlgaMonayExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired private MessageSource messageSource;

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
		System.out.println("b");
		String msgUsu = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String msgDev = ExceptionUtils.getRootCauseMessage(ex);		
		List<Erro> erros = Arrays.asList(new Erro(msgUsu, msgDev));				
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException ex, WebRequest request){
		System.out.println("a");
		String msgUsu = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String msgDev = ex.getMessage().toString();		
		
		List<Erro> erros = Arrays.asList(new Erro(msgUsu, msgDev));		
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);		
	}

	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, 
															      HttpStatusCode status, WebRequest request) {					
		System.out.println("c");
		String msgUsu = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String msgDev = ex.getCause() == null ? ex.toString() : ex.getCause().toString();		
		List<Erro> erros = Arrays.asList(new Erro(msgUsu, msgDev));		
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<Erro> erros = CriarListaDeErros(ex.getBindingResult()); 
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> CriarListaDeErros(BindingResult bindingResult) {
		List<Erro> erros = new ArrayList<>();		
		for (FieldError fieldError: bindingResult.getFieldErrors()) {
			String msgUsu = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String msgDev = fieldError.toString();							
			erros.add(new Erro(msgUsu, msgDev));		
		}			
		return erros;
	}
	
	public static class Erro {
		private String msgUsuario;
		private String msgDesenv;
		public Erro(String msgUsuario, String msgDesenv) {
			super();
			this.msgUsuario = msgUsuario;
			this.msgDesenv = msgDesenv;
		}
		public String getMsgUsuario() {
			return msgUsuario;
		}
		public void setMsgUsuario(String msgUsuario) {
			this.msgUsuario = msgUsuario;
		}
		public String getMsgDesenv() {
			return msgDesenv;
		}
		public void setMsgDesenv(String msgDesenv) {
			this.msgDesenv = msgDesenv;
		}
		
		
	}
	
	
}
