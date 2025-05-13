package com.ck.wi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HmbrandtApiRestApplication {



	public static void main(String[] args) {
		System.out.println("DB_URL env: " + System.getenv("DB_URL"));
		System.out.println("DB_USER env: " + System.getenv("DB_USER"));

		SpringApplication.run(HmbrandtApiRestApplication.class, args);
	}

}
