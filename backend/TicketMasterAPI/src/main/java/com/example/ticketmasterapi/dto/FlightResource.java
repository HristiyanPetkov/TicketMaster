package com.example.ticketmasterapi.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FlightResource {
    private String flightNumber;
    private Timestamp departure_date;
    private Timestamp arrival_date;
    private String arrivalAirport;
    private String departureAirport;
    private Float price;
}
