package com.kopo.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class KopoTradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(KopoTradingApplication.class, args);
	}

}
