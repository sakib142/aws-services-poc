package com.mktx.rds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.mktx.rds.repository")
public class EmsPostgresPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmsPostgresPocApplication.class, args);
	}

}
