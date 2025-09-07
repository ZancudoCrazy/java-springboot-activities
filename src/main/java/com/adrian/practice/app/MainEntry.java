package com.adrian.practice.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javafx.application.Application;

/**
 * Main class of the Spring Boot application.
 * It contains the entry point of the application.
 * It is annotated with @SpringBootApplication to enable
 * component scanning, auto-configuration, and property support.
 */
@SpringBootApplication
public class MainEntry {

	public static void main(String[] args) {
		Application.launch(FxApplication.class, args);
	}

}
