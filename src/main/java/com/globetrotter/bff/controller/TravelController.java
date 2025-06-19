package com.globetrotter.bff.controller;

import com.globetrotter.bff.client.OpenAiServiceClient;
import com.globetrotter.bff.client.TravelServiceClient;
import com.globetrotter.bff.model.FlightSearchRequest;
import com.globetrotter.bff.model.FlightSearchResponse;
import com.globetrotter.bff.model.TextSearchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/travel")
@Tag(name = "Travel", description = "Travel-related endpoints")
@RequiredArgsConstructor
public class TravelController {

    private final TravelServiceClient travelServiceClient;
    private final OpenAiServiceClient openAiServiceClient;

    // Simple mapping of city names to airport codes
    private static final Map<String, String> cityToAirportCode = new HashMap<>();
    static {
        cityToAirportCode.put("New York", "JFK");
        cityToAirportCode.put("London", "LHR");
        // Add more mappings as needed
    }

    @Operation(summary = "Search for flights")
    @GetMapping("/flights")
    public FlightSearchResponse getFlights(
            @Parameter(description = "Origin airport code (e.g., SFO)") @RequestParam String from,
            @Parameter(description = "Destination airport code (e.g., JFK)") @RequestParam String to,
            @Parameter(description = "Flight date in ISO 8601 format (YYYY-MM-DD)") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return travelServiceClient.getFlights(from, to, date);
    }

    @PostMapping("/flights/text-search")
    public FlightSearchResponse searchFlightsByText(@RequestBody TextSearchRequest request) {
        // First, use OpenAI to extract flight details from the text
        FlightSearchRequest flightRequest = openAiServiceClient.extractFlightFields(request.getText());

        // Map city names to airport codes
        // flightRequest.setFromAirportCode(cityToAirportCode.getOrDefault(flightRequest.getFromAirportCode(),
        // flightRequest.getFromAirportCode()));
        // flightRequest.setToAirportCode(cityToAirportCode.getOrDefault(flightRequest.getToAirportCode(),
        // flightRequest.getToAirportCode ()));

        // Debug log
        System.out.println("FlightSearchRequest to travel service: " + flightRequest);

        // Then, use the extracted details to search for flights
        return travelServiceClient.searchFlights(flightRequest);
    }
}
