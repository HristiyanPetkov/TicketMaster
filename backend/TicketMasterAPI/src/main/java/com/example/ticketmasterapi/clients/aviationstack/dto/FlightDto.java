package com.example.ticketmasterapi.clients.aviationstack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    public Flight flight;
    public Airport departure;
    public Airport arrival;
}
