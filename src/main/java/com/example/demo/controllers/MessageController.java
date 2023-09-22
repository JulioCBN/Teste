package com.example.demo.controllers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/messages", "/message"})
public class MessageController {

    private final MessageSource messageSource;

    public MessageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/{name}")
    public ResponseEntity<String> getGreetingMessage(@PathVariable String name) {
       
    	System.out.println("teste");
    	
    	String message = messageSource.getMessage(
                "withName",
                new Object[]{name},
                LocaleContextHolder.getLocale()
        );

        return ResponseEntity.ok(message);
    }
}