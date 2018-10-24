package com.pony.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataSourceConnect {

    @Bean
    @Profile("default")
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder
            .create()
            .url("jdbc:postgresql://localhost:5432/pony")
            .username("admin")
            .password("azerty1234")
            .driverClassName("org.postgresql.Driver")
            .build();

        return dataSource;
    }
}