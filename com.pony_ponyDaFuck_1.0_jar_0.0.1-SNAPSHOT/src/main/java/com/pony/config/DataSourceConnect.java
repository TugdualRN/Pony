package com.pony.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataSourceConnect {

    @Value("${mailing.host}")
    private String _url;

    @Value("${mailing.host}")
    private String _username;

    @Value("${mailing.host}")
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