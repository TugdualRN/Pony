package com.pony;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.pony")
public class PonyDaFuckApplication {

	public static void main(String[] args) {
		SpringApplication.run(PonyDaFuckApplication.class, args);
	}
}