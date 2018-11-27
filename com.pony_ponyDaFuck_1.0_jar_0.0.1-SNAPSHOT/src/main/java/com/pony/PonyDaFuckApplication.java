package com.pony;

import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.pony")
//@EnableWebMvc
//@EnableAutoConfiguration
public class PonyDaFuckApplication {

    public static boolean isDevelopment = false;

	public static void main(String[] args) {
		
		SpringApplication.run(PonyDaFuckApplication.class, args);
	}
}