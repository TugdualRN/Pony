package com.pony.spring.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //TEST
    @Bean
    public AuthenticationProvider userAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
        auth.inMemoryAuthentication()
                .withUser("USER@USER.COM").password("123").roles("USER").and()
                .withUser("ADMIN@ADMIN.COM").password("123").roles("USER", "ADMIN").and()
                .withUser("MASTER@MASTER.COM").password("123").roles("USER", "ADMIN","MASTER");
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
        web.ignoring().antMatchers("/webjars/**");
//        web.ignoring().antMatchers("/css/**");
//        web.ignoring().antMatchers("/scss/**");
//        web.ignoring().antMatchers("/fonts/**");
//        web.ignoring().antMatchers("/images/**");
//        web.ignoring().antMatchers("/js/**");
//        web.ignoring().antMatchers("/plugins/**");
//        web.ignoring().antMatchers("/vendors/**");
    }

//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // Static ressources from both WEB-INF and webjars
//        registry
//                .addResourceHandler("/resources/**")
//                .addResourceLocations("/resources/");
//       }

        @Override
     protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // Authorizations
            .antMatchers("/").permitAll()
            .antMatchers("/home").permitAll()
            .antMatchers("/shop").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/login*").anonymous()
            .antMatchers("/register").anonymous()
            .antMatchers("/reset-password").anonymous()
            .antMatchers("/news/**").permitAll()
            .antMatchers(HttpMethod.POST, "/charge").permitAll()
            .antMatchers("/logout").authenticated()
            .antMatchers("/connect/**").authenticated()
            .antMatchers("/create-news").hasRole("ADMIN")
            .antMatchers("/admin/**").hasAnyRole("MASTER")
            .antMatchers("/user/**").hasAnyRole("USER")

            // Login
            .and()
                .formLogin()
                .usernameParameter("login")
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
                .defaultSuccessUrl("/")
                .failureUrl("/login/fail")

            // Logout
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")

            // RememberMe
            // .and()
            // .rememberMe()
            // .rememberMeParameter("remember-me")
            // .tokenRepository(persistentTokenRepository())
            // .tokenValiditySeconds(86400)

            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
            // .and()
//             .and()
//             .csrf().disable()
            ;
        http.csrf().disable();
    }
}