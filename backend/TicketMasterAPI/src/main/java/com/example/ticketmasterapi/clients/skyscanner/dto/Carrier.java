package com.example.ticketmasterapi.clients.skyscanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
public class Carrier {
    public String name;
    public String iata;
}
