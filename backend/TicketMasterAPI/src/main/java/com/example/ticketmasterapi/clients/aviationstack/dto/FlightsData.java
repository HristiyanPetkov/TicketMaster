package com.example.ticketmasterapi.clients.aviationstack.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FlightsData {
    public List<FlightDto> data;
}
