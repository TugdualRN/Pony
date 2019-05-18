package com.pony.spring.mvc;

import com.pony.spring.monitoring.LoggerMiddleware;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.Charset;
import java.util.*;

@Configuration
@EnableWebMvc
@ComponentScan("com.pony")

@SpringBootApplication(scanBasePackages={"com.pony"})
//public class MvcConfig extends WebMvcConfigurationSupport {
public class MvcConfig implements WebMvcConfigurer {

    private final DispatcherServlet _dispatcherServlet;

    @Autowired
    public MvcConfig(ApplicationContext applicationContext, DispatcherServlet dispatcherServlet) {
        super();
        _dispatcherServlet = dispatcherServlet;
        System.out.println(applicationContext.getDisplayName());
    }

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    public ClassLoaderTemplateResolver templateResolver() {

        var templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("/templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public  ISpringTemplateEngine templateEngine() {

        var templateEngine = new SpringTemplateEngine();
        templateEngine.addDialect(new Java8TimeDialect());
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(templateResolver());

        return templateEngine;
    }

    @Bean
    @Description("Thymeleaf view resolver")
    public ViewResolver viewResolver() {

        var viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");

        return viewResolver;
    }
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        converters.add(stringHttpMessageConverter);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.FRENCH);
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(

                "/css/**",
                "/fonts/**",
                "/favicon.ico",
                "/images/**",
                "/img/**",
                "/js/**",
                "/plugins/**",
                "/webjars/**",
                "/build/**")
                .addResourceLocations(
                        ("classpath:/static/css/"),
                        ("classpath:/static/fonts/"),
                        ("classpath:/static/images/"),
                        ("classpath:/static/js/"),
                        ("classpath:/static/plugins/"),
                        ("classpath:/static/webjars/**"),
                        "classpath:/static/build/");
    }

    @Bean
    public CommandLineRunner getCommandLineRunner() {
        _dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return args -> {
        };
    }

    @Bean
    public MappedInterceptor myInterceptor() {
        HandlerInterceptorAdapter loggerMiddleware = new LoggerMiddleware();
        return new MappedInterceptor(new String[0], loggerMiddleware);
    }

}



