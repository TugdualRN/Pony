/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.config;

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
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Gotug
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

     @Autowired
     UserDetailsService userDetailsService;

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
                  .antMatchers("/home").authenticated()
                  .antMatchers("/login*").permitAll()
                  .anyRequest().authenticated()
                  //                  .anyRequest().hasAnyRole("USER")
                  .antMatchers("/admin/**").hasAnyRole("ADMIN")
                  .antMatchers("/user/**").hasAnyRole("USER")
                  .and()
                  .formLogin()
                  .usernameParameter("login")
                  .passwordParameter("password")
                  .loginProcessingUrl("/login")
                  .loginPage("/login")
                  .permitAll()
                  .defaultSuccessUrl("/home")
                  .failureUrl("/login-error")
                  .and()
                  .logout()
                  .logoutUrl("/logout")
                  .logoutSuccessUrl("/login")
                  .and()
                  .sessionManagement()
                  .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                  .maximumSessions(1)
                  .and()
                  .and()
                  .csrf().disable();
     }
}
