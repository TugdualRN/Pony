/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
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
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/scss/**", "/fonts/**", "/images/**", "/js/**", "/plugins/**");
    }

     @Override
     protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
            // Authorizations
            .antMatchers("/").permitAll()
            .antMatchers("/home").permitAll()
            .antMatchers("/login*").anonymous()
            .antMatchers("/register").anonymous()
            .antMatchers("/reset-password").anonymous()
            .antMatchers("/create-news").authenticated()
            .antMatchers("/logout").authenticated()
            .anyRequest().authenticated()
            .antMatchers("/admin/**").hasAnyRole("ADMIN")
            .antMatchers("/user/**").hasAnyRole("USER")
            .and()

            // Login
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

            // // RememberMe
            // .and()
            // .rememberMe()
            // .rememberMeParameter("remember-me")
            // .tokenRepository(persistentTokenRepository())
            // .tokenValiditySeconds(86400)

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .maximumSessions(1)
            .and()
            .and()
            .csrf().disable();
    }
}