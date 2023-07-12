package com.example.ticketmasterapi.clients.skyscanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Result {
    public Map<String, FlightDtoSc> quotes;
    public Map<String, Carrier> carriers;
}
