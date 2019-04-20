package com.pony.spring.mvc;

import com.pony.spring.monitoring.LoggerMiddleware;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.apache.commons.codec.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.ISpringTemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Locale;


@Configuration
@EnableWebMvc
@SpringBootApplication(scanBasePackages={"com.pony"})
@ComponentScan("com.pony")


//public class MvcConfig extends WebMvcConfigurationSupport {
public class MvcConfig extends WebMvcConfigurerAdapter {

    private final DispatcherServlet _dispatcherServlet;
    private ApplicationContext applicationContext;

    @Autowired
    public MvcConfig(ApplicationContext applicationContext, DispatcherServlet dispatcherServlet) {
        super();

        _dispatcherServlet = dispatcherServlet;

        System.out.println(applicationContext.getDisplayName());
    }

    /**
     * STEP 1 - Create SpringTemplateEngine
     */

    @Bean
    @Autowired
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addDialect(new Java8TimeDialect());
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(sec);
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }
    // Baeldung model
//    private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.addDialect(new Java8TimeDialect());
//        engine.setTemplateResolver(templateResolver);
//        return engine;
//    }

    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(

                "/css/**",
                "/fonts/**",
                "/images/**",
                "/img/**",
                "/webjars/**",
                "/webjars/",
                "/js/**",
                "/fonts/nucleo/**",
                "/plugins/**")
//                "/vendors/**")
//                "/scss/**")
                .addResourceLocations(
                        "classpath:/static/css/",
                        "classpath:/static/fonts/",
                        "classpath:/static/images/",
                        "classpath:/static/webjars/**",
                        "classpath:/static/webjars/",
                        "classpath:/static/img/",
                        "classpath:/static/js/",
                        "classpath:/fonts/nucleo/**",
                        "classpath:/static/plugins/");
//                        "classpath:/static/scss/",
//                        "classpath:/static/vendors/");
    }


    /**
     * STEP 2 - Internationalization
     */
    @Bean(name = "messageSource")
    public MessageSource messageSource() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding(CharEncoding.UTF_8);
        //        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }


    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource( messageSource() );
        return validator;
    }

    //    @Bean(name="localeResolver")
//    public CookieLocaleResolver localeResolver() {
//
//        CookieLocaleResolver resolver = new CookieLocaleResolver();
//        resolver.setDefaultLocale(Locale.FRENCH);
//        resolver.setCookieName("lang");
////        resolver.setCookieMaxAge(-1);
//
//        return resolver;
//    }
//
    @Bean(name="localeResolver")
    public LocaleResolver localeResolver(){
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        resolver.setDefaultLocale(Locale.FRENCH);
        return resolver;
    }

    @Bean(name="LocaleChangeInterceptor")
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    //    @Bean(name="addInterceptors")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
    //    @Bean
//    DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
//        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
//        handler.setDefaultRolePrefix("MASTER");
//        handler.setDefaultRolePrefix("USER");
//        handler.setDefaultRolePrefix("ADMIN");
//        return handler;
//    }

    //Time gestion

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
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

