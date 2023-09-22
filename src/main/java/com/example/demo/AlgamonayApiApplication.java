package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlgamonayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgamonayApiApplication.class, args);
	}

//	@Bean
//	public MessageSource messageSource() {
//	    ReloadableResourceBundleMessageSource messageSource
//	      = new ReloadableResourceBundleMessageSource();
//	    messageSource.setDefaultLocale(Locale.US);
//	    messageSource.setBasename("classpath:messages");
//	    messageSource.setDefaultEncoding("UTF-8");
//	    return messageSource;
//	}
//	   
//	@Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver slr = new SessionLocaleResolver();
//        slr.setDefaultLocale(Locale.ENGLISH);
//        return slr;
//    }
//	
//	@Bean
//	public LocalValidatorFactoryBean validator() {
//	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
//	    bean.setValidationMessageSource(messageSource());
//	    return bean;
//	}	
	
//	@Bean
//    public MessageSource messageSource() {
//        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
//        messageSource.setBasename("classpath:messages");
//        messageSource.setDefaultEncoding("UTF-8");
//        //Locale.setDefault(new Locale("pt", "BR"));
//        //messageSource.setDefaultLocale(new Locale("pt", "BR"));        
//        return messageSource;
//    }

}
