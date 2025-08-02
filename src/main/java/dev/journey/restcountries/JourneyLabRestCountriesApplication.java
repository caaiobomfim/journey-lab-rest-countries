package dev.journey.restcountries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "dev.journey.restcountries")
public class JourneyLabRestCountriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(JourneyLabRestCountriesApplication.class, args);
	}

}
