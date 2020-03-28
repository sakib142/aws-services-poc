package com.trax.ems.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Main class to start the application
 * 
 *
 */
@SpringBootApplication
@EntityScan({ "com.trax.ems.model.security" })
public class StartSecurityFeedService {

	public StartSecurityFeedService() {

	}

	public static void main(String[] args) {
		SpringApplication.run(StartSecurityFeedService.class, args);
	}
}
