package com.pony.config;

import com.pony.spring.data.DataSourceConnect;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DataSourceConnectTest extends DataSourceConnect {

    @Bean
    @Profile("test")
    @Override
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder
            .create()
            .url("jdbc:postgresql://localhost:5432/pony")
            .username("postgres")
            .password("ebola")
            .driverClassName("org.postgresql.Driver")
            .build();

        return dataSource;
    }
}