package io.javabrains.ratingsdataservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableEurekaClient
public class RatingsDataServiceApplication {

	public static void main(String[] args) {

		run(RatingsDataServiceApplication.class, args);
	}

}
