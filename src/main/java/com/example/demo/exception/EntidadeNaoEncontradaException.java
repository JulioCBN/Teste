package com.example.demo.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) // , reason = "Entidade não encontrada"
public class EntidadeNaoEncontradaException extends RuntimeException { //ResponseStatusException {

	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);	
		System.out.println("-----");
		System.out.println(this.getMessage());
	}
	
	public EntidadeNaoEncontradaException(String entidade, Long id) {
		this(String.format("Não existe cadastro de %s com código %d", entidade, id));
	}

	public EntidadeNaoEncontradaException(String entidade, String id) {
		this(String.format("Não existe cadastro de %s com código %s", entidade, id));
	}
	
	public EntidadeNaoEncontradaException(String e1, Long id1, String e2, Long id2, Boolean fem) {
		this(String.format("Não existe um cadastro de %s com código %d para " + (fem ? "a": "o") + " %s de código %d", e1, id1, e2, id2));
	}

}
