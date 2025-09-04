package com.adrian.practice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class of the Spring Boot application.
 * It contains the entry point of the application.
 * It is annotated with @SpringBootApplication to enable
 * component scanning, auto-configuration, and property support.
 */
@SpringBootApplication
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

}
