/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pony.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Gotug
 */
@Configuration
public class DataSourceConnect {

     @Bean
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
