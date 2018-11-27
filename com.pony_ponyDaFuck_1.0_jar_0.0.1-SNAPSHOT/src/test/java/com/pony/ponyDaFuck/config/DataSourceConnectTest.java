//package com.pony.ponyDaFuck.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import javax.sql.DataSource;
//
//import com.pony.spring.data.DataSourceConnect;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//
//@Configuration
//public class DataSourceConnectTest extends DataSourceConnect {
// 
//    @Bean
//    @Profile("test")
//    @Override
//    public DataSource dataSource() {
//        DataSource dataSource = DataSourceBuilder
//            .create()
//            .url("jdbc:postgresql://localhost:5432/test")
//            .username("pony")
//            .password("azerty1234")
//            .driverClassName("org.postgresql.Driver")
//            .build();
//
//        return dataSource;
//    }
//}