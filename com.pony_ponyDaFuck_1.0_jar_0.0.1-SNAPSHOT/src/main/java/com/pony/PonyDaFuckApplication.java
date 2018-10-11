package com.pony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pony")
public static class PonyDaFuckApplication {

    public static boolean isDevelopment = false;

	public static void main(String[] args) {
		
		SpringApplication.run(PonyDaFuckApplication.class, args);
	}
}