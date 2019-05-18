package com.pony.spring.security;

import com.pony.spring.data.DataSourceConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private final DataSourceConnect dataSourceConnect;

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, DataSourceConnect dataSourceConnect) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.dataSourceConnect = dataSourceConnect;
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setDefaultRolePrefix("MASTER");
        handler.setDefaultRolePrefix("ADMIN");
        handler.setDefaultRolePrefix("USER");
        return handler;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(new BCryptPasswordEncoder());
        auth.inMemoryAuthentication()
                .withUser("USER@USER.COM").password(passwordEncoder().encode("123")).roles("USER")
                .and()
                .withUser("ADMIN@ADMIN.COM").password(passwordEncoder().encode("123")).roles("ADMIN")
                .and()
                .withUser("MASTER@MASTER.COM").password(passwordEncoder().encode("123")).roles("MASTER");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // Authorizations
                .antMatchers("/").permitAll()
                .antMatchers("/login*").anonymous()
                .antMatchers("/register").anonymous()
                .antMatchers("/reset-password").anonymous()
                .antMatchers("/logout").authenticated()
                .antMatchers("/connect/**").authenticated()
                .antMatchers("/home").authenticated()
                .antMatchers("/welcome").authenticated()
                .antMatchers("/profile").authenticated()
                .antMatchers("/shop").authenticated()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/news/**").permitAll()
                .antMatchers(HttpMethod.POST, "/charge").permitAll()
//                .antMatchers("/create-news").hasRole("ADMIN")
                .antMatchers("/create-news").permitAll()
                .antMatchers("/admin/**").hasAnyRole("MASTER")
                .antMatchers("/admin/**").hasAnyRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER")
                .antMatchers("/static/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers( "/favicon.ico").anonymous()
//                .anyRequest().authenticated()


                // Login
                .and()
                .formLogin()
                .usernameParameter("login")
                .passwordParameter("password")
                .loginPage("/")
                .loginProcessingUrl("/login")
                .permitAll()
                .defaultSuccessUrl("/home")
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