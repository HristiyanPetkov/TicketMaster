package com.example.ticketmasterapi.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FlightResource {
    private String arrival;
    private String departure;
    private String flightNumber;
    private Timestamp date;
    private float price;
}
