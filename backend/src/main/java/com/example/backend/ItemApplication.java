package com.example.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

@SpringBootApplication
public class ItemApplication {
	@Value("${security.jwt.token.secret-key}")
	private String envKey;

	public static void main(String[] args) {
		SpringApplication.run(ItemApplication.class, args);
	}
	
	@PostConstruct
	public void printEnv() {
		System.out.println("Sec : " + envKey);
	}
}


