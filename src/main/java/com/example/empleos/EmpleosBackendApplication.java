package com.example.empleos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmpleosBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpleosBackendApplication.class, args);
	}


}
