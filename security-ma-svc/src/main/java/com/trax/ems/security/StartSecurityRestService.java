package com.trax.ems.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to start the application
 */
@SpringBootApplication
public class StartSecurityRestService {

	public StartSecurityRestService() {
	}

	public static void main(String[] args) {
		SpringApplication.run(StartSecurityRestService.class, args);
	}
}
