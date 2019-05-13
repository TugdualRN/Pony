package com.pony.spring.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DataSourceConnect {

    @Value("${database.url}")
    private String _url;

    @Value("${database.userName}")
    private String _username;

    @Value("${database.password}")
    private String _password;

    @Bean
    @Profile("default")
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder
            .create()
            .url(_url)
            .username(_username)
            .password(_password)
            .driverClassName("org.postgresql.Driver")
            .build();

        return dataSource;
    }
}