package com.cts.sb;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
	}
	
	@Bean
	public LocalDate today() {
		return LocalDate.now();
	}	
	
	@Bean
	public LocalDate yesterday() {
		return LocalDate.now().minusDays(1);
	}
}
