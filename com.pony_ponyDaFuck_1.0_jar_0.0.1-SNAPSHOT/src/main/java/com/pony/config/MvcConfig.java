/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.config;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 *
 * @author Gotug
 */


@Configuration
@ComponentScan("com.pony")
public class MvcConfig extends WebMvcConfigurerAdapter {


     private final ApplicationContext applicationContext;
     
     @Autowired
     public MvcConfig(ApplicationContext applicationContext) {
          super();
          this.applicationContext = applicationContext;
     }



     /*
    * STEP 1 - Create SpringTemplateEngine
    * */
     @Bean
     public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) {
          SpringTemplateEngine templateEngine = new SpringTemplateEngine();
          templateEngine.setTemplateResolver(templateResolver);
          templateEngine.addDialect(sec);
          return templateEngine;
     }

     /*
    * STEP 2 - Internationalization 
    * */
     @Bean
     public MessageSource messageSource() {
          ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
          messageSource.setBasename("classpath:messages");
          messageSource.setDefaultEncoding("UTF-8");
          return messageSource;
     }

     @Bean
     public LocaleResolver localeResolver() {
          CookieLocaleResolver resolver = new CookieLocaleResolver();
          resolver.setDefaultLocale(Locale.ENGLISH);
//          resolver.setCookieName("PonyCookie");
//          resolver.setCookieMaxAge(4800);
          return resolver;
     }
 @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
	LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
	localeChangeInterceptor.setParamName("lang");
	return localeChangeInterceptor;
    }
     @Override
     public void addInterceptors(InterceptorRegistry registry) {
//          LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//          interceptor.setParamName("language");
          registry.addInterceptor(localeChangeInterceptor());
     }

}
