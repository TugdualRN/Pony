package com.pony.spring.mvc;

import com.pony.spring.monitoring.LoggerMiddleware;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.List;
import java.util.Locale;


@Configuration
@EnableWebMvc
//@EnableAutoConfiguration
@SpringBootApplication
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

    @Autowired
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(sec);
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }


    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**")
//                .addResourceLocations("classpath:/resources/","classpath:/other-resources/");
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(

                "/css/**",
                "/fonts/**",
                "/images/**",
                "/img/**",
                "/js/**",
                "/fonts/nucleo/**",
                "/plugins/**")
//                "/vendors/**")
//                "/scss/**")
                .addResourceLocations(
                        "classpath:/static/css/",
                        "classpath:/static/fonts/",
                        "classpath:/static/images/",
                        "classpath:/static/img/",
                        "classpath:/static/js/",
                        "classpath:/static/js/",
                        "classpath:/fonts/nucleo/**",
                        "classpath:/static/plugins/");
//                        "classpath:/static/scss/",
//                        "classpath:/static/vendors/");
    }


     /**
     * STEP 2 - Internationalization
     */
//    @Bean
//    public MessageSource messageSource() {
//
//        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
//        messageSource.setBasename("classpath:messages");
//        messageSource.setDefaultEncoding("UTF-8");
//
//        return messageSource;
//    }


    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

//    @Bean
//    DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
//        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
//        handler.setDefaultRolePrefix("MASTER");
//        handler.setDefaultRolePrefix("USER");
//        handler.setDefaultRolePrefix("ADMIN");
//        return handler;
//    }

    @Bean
    public CookieLocaleResolver localeResolver() {

        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.FRENCH);
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
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        // LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
//        // interceptor.setParamName("language");
//
////        registry.addInterceptor(localeChangeInterceptor());
//
//
////        registry.addInterceptor(rootDomainInterceptor())
////        .excludePathPatterns("/css/**", "/scss/**", "/fonts/**", "/images/**", "/js/**", "/plugins/**", "/vendors/**");
//
////        registry
////                .addInterceptor(localeChangeInterceptor())
////                .excludePathPatterns("/css/**", "/scss/**", "/fonts/**", "/images/**", "/js/**", "/plugins/**", "/vendors/**");
////
////    }


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

//
//
//@Configuration
//@ComponentScan("com.pony")
//@ConditionalOnClass({SpringTemplateEngine.class})
//@EnableConfigurationProperties({ThymeleafProperties.class})  //no sense rolling our own.
//@AutoConfigureAfter({WebMvcAutoConfiguration.class})
//public class MvcConfig implements ApplicationContextAware {
//
//    private ApplicationContext applicationContext;
//
//    @Autowired
//    private ThymeleafProperties properties;
//
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }
//
//    private final DispatcherServlet _dispatcherServlet;
//
//    @Autowired
//    public MvcConfig(ApplicationContext applicationContext, DispatcherServlet dispatcherServlet) {
//        super();
//
//        _dispatcherServlet = dispatcherServlet;
//
//        System.out.println(applicationContext.getDisplayName());
//    }
//    @Bean
//    public ViewResolver viewResolver() {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(templateEngine());
//        resolver.setCharacterEncoding("UTF-8");
//        return resolver;
//    }
//
//    @Bean
//    //made this @Bean (vs private in Thymeleaf migration docs ), otherwise MessageSource wasn't autowired.
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//        engine.setTemplateResolver(templateResolver());
//        return engine;
//    }
//
//    private ITemplateResolver templateResolver() {
//        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
//        resolver.setApplicationContext(applicationContext);
//        resolver.setPrefix(this.properties.getPrefix());
//        resolver.setSuffix(this.properties.getSuffix());
//        resolver.setTemplateMode(this.properties.getMode());
//        resolver.setCacheable(this.properties.isCache());
//        return resolver;
//    }

