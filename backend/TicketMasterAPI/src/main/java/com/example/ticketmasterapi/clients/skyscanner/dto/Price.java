package com.example.ticketmasterapi.clients.skyscanner.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price {
    public Float amount;
}
