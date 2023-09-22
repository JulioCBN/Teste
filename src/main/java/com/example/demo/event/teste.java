package com.example.demo.event;

import java.net.URI;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;

public class teste {

	public static void funcao(Long codigo, HttpServletResponse response) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();	

		HttpServletResponse httpServletResponse = response;
		httpServletResponse.setHeader("Location", uri.toASCIIString());
	}
	
}
