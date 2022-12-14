package br.firzen.cacacapsulas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CacacapsulasApplication {

	public static void main(String[] args) {
		SpringApplication.run(CacacapsulasApplication.class, args);
	}
}
