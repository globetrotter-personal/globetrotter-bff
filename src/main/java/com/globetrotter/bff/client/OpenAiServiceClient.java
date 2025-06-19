package com.globetrotter.bff.client;

import com.globetrotter.bff.model.FlightSearchRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "openai-service", url = "${services.openai.url}")
public interface OpenAiServiceClient {
    
    @PostMapping("/ai/openai/extract-flight-fields")
    FlightSearchRequest extractFlightFields(@RequestBody String text);
} 