package com.globetrotter.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GlobetrotterBffApplication {
    public static void main(String[] args) {
        SpringApplication.run(GlobetrotterBffApplication.class, args);
    }
} 