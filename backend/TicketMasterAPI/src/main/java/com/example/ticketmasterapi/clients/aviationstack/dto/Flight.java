package com.example.ticketmasterapi.clients.aviationstack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    public Timestamp flight_date;
    public Airport departure;
    public Airport arrival;
}
