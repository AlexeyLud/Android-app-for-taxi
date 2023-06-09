package com.alex.serv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.alex.serv.controllers", "com.alex.serv.services", "com.alex.serv.serviceImpl"})
@EntityScan(basePackages = "com.alex.serv.entities")
@EnableJpaRepositories(basePackages = "com.alex.serv.repositories")
public class ServerApplication {

	private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		logger.info("Yep server started!");
	}

}
