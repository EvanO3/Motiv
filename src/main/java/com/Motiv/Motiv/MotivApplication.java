package com.Motiv.Motiv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MotivApplication {

	public static void main(String[] args) {
		SpringApplication.run(MotivApplication.class, args);
	}

}
