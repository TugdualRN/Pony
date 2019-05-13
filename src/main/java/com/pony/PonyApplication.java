package com.pony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication(scanBasePackages = "com.pony")
@EnableWebMvc

public class PonyApplication {

//	public class PonyDaFuckApplication extends SpringBootServletInitializer {
//		protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//			return builder.sources(SpringBootApplication.class);
//		}
//		public static boolean isDevelopment = false;

		public static void main(String[] args) {

		SpringApplication.run(PonyApplication.class, args);
	}

}
