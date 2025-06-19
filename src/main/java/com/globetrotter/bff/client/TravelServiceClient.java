package com.globetrotter.bff.client;

import com.globetrotter.bff.model.FlightSearchRequest;
import com.globetrotter.bff.model.FlightSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import feign.Request;

import java.time.LocalDate;

@FeignClient(name = "travel-service", url = "${services.travel.url}", configuration = FeignConfig.class)
public interface TravelServiceClient {

    @GetMapping("/travel/flights")
    FlightSearchResponse getFlights(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("date") LocalDate date);

    @PostMapping("/travel/flights/search")
    FlightSearchResponse searchFlights(@RequestBody FlightSearchRequest request);
}

@Configuration
class FeignConfig {
    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(5000, 90000); // connectTimeout, readTimeout in milliseconds
    }
}
