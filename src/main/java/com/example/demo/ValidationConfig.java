package com.example.demo;

//import java.util.Locale;
//
//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.support.ReloadableResourceBundleMessageSource;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//import org.springframework.web.servlet.LocaleResolver;
//import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//@Configuration
//public class ValidationConfig {
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
//        slr.setDefaultLocale(Locale.US);
//        return slr;
//    }
//	
//	@Bean
//	public LocalValidatorFactoryBean validator() {
//	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
//	    bean.setValidationMessageSource(messageSource());
//	    return bean;
//	}	
//	
//	//
////	@Bean
////	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
////		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
////		bean.setValidationMessageSource(messageSource);
////		return bean;
////	}
////	
////	@Bean
////	public MessageSource messageSource() {
////	    ReloadableResourceBundleMessageSource messageSource
////	      = new ReloadableResourceBundleMessageSource();
////	    messageSource.setDefaultLocale(Locale.US);
////	    messageSource.setBasename("classpath:messages");
////	    messageSource.setDefaultEncoding("UTF-8");
////	    return messageSource;
////	}	
//	
//}
