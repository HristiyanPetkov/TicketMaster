package com.example.ticketmasterapi.clients.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class Flight {
    public LocalDate flight_date;
    public Airport departure;
    public Airport arrival;
}
