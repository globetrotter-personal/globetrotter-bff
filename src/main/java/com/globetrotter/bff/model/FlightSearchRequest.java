package com.globetrotter.bff.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FlightSearchRequest {
    private String from;
    private String to;
    private String fromAirportCode;
    private String toAirportCode;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int numberOfPassengers;
    private String travelClass;
} 