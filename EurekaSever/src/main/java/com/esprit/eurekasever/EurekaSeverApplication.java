package com.esprit.eurekasever;

import com.netflix.discovery.EurekaNamespace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class EurekaSeverApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaSeverApplication.class, args);
	}

}
