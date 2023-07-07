package com.example.ticketmasterapi.clients.amadeus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class FlightDtoAm {
    public String source;
    public Timestamp lastTicketingDate;
    public Itineraries itineraries;
    public Price price;
}
