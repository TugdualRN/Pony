package com.pony.ponyDaFuck;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;

import com.pony.config.DataSourceConnect;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

@Configuration
public class DataSourceConnectTest extends DataSourceConnect {
 
    @Bean
    @Profile("test")
    @Override
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder
            .create()
            .url("jdbc:postgresql://localhost:5432/test")
            .username("pony")
            .password("azerty1234")
            .driverClassName("org.postgresql.Driver")
            .build();

        return dataSource;
    }
}