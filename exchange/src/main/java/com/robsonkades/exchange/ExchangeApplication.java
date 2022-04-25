package com.robsonkades.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ExchangeApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExchangeApplication.class, args);
	}
}
