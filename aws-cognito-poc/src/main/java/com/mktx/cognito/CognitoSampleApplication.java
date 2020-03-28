package com.mktx.cognito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CognitoSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CognitoSampleApplication.class, args);
	}

}
