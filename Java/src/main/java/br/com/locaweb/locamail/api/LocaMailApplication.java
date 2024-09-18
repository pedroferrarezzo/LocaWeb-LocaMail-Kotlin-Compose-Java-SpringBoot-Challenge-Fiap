package br.com.locaweb.locamail.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LocaMailApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocaMailApplication.class, args);
	}

}
