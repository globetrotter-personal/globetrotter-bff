package com.globetrotter.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (exclude = {org.springframework.ai.autoconfigure.mcp.client.SseHttpClientTransportAutoConfiguration.class})
public class GlobetrotterBffApplication {

    public static void main(String[] args) {
        SpringApplication.run(GlobetrotterBffApplication.class, args);
    }

}
