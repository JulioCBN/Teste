package com.example.demo.event;

import org.springframework.context.ApplicationEvent;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;

@Getter
public class RecursoCriadoEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse httpServletResponse;
	private Long codigo;

	public RecursoCriadoEvent(Object source, HttpServletResponse httpServletResponse, Long codigo) {
		super(source);
		this.httpServletResponse = httpServletResponse;
		this.codigo = codigo;
	}
}
