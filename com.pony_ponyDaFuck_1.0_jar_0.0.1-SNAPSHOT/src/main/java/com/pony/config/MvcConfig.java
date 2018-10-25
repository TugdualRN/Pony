package com.pony.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.pony.config.LoggerMiddleware;

@Configuration
@ComponentScan("com.pony")
public class MvcConfig extends WebMvcConfigurerAdapter {

    private final ApplicationContext _applicationContext;
    private final DispatcherServlet _dispatcherServlet;
    
    @Autowired
    public MvcConfig(ApplicationContext applicationContext, DispatcherServlet dispatcherServlet) {
        super();
        
        _applicationContext = applicationContext;
        _dispatcherServlet = dispatcherServlet;

        System.out.println(_applicationContext.getDisplayName());
    }

    /**
     * STEP 1 - Create SpringTemplateEngine
     */
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(sec);

        return templateEngine;
    }

    /**
     * STEP 2 - Internationalization 
     */
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
        resolver.setCookieName("lang");
        resolver.setCookieMaxAge(-1);

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
        
        // LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        // interceptor.setParamName("language");
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public CommandLineRunner getCommandLineRunner() {
        _dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        
        return args -> {};
    }

    @Bean
    public MappedInterceptor myInterceptor() {
        HandlerInterceptorAdapter loggerMiddleware = new LoggerMiddleware();
        
        return new MappedInterceptor(new String[0], loggerMiddleware);
    }
}