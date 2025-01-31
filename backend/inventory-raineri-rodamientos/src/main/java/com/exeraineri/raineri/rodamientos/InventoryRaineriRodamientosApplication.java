package com.exeraineri.raineri.rodamientos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InventoryRaineriRodamientosApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryRaineriRodamientosApplication.class, args);
	}

}
