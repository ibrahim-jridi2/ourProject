package com.example.API_GATEWAY_SERVE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayServeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServeApplication.class, args);
	}

}
