package com.modulecomment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableEurekaClient
@SpringBootApplication
@EnableScheduling
public class ModuleCommentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleCommentApplication.class, args);
    }

}
