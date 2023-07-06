package com.example.ticketmasterapi.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FlightResource {
    private String arrival;
    private String departure;
    private Timestamp date;
    private String flightNumber;
    private float price;
    public float getPrice() {
        return price;
    }
}
