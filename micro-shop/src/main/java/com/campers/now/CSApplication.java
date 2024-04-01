package com.campers.now;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableScheduling
@EnableEurekaClient

public class CSApplication {

    public static void main(String[] args) {
        SpringApplication.run(CSApplication.class, args);
    }

}
