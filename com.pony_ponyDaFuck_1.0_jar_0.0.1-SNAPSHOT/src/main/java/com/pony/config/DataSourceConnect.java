package com.pony.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConnect {

    @Bean
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder
            .create()
            .url("jdbc:postgresql://localhost:5432/pony")
            .username("pony")
            .password("azerty1234")
            .driverClassName("org.postgresql.Driver")
            .build();

        return dataSource;
    }
}