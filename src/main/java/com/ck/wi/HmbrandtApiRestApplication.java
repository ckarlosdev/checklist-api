package com.ck.wi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HmbrandtApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmbrandtApiRestApplication.class, args);
	}

}
