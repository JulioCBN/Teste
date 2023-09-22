package com.example.demo.event.listener;

import java.net.URI;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.event.RecursoCriadoEvent;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
	
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(recursoCriadoEvent.getCodigo()).toUri();	

		HttpServletResponse httpServletResponse = recursoCriadoEvent.getHttpServletResponse();
		httpServletResponse.setHeader("Location", uri.toASCIIString());
	}

}
