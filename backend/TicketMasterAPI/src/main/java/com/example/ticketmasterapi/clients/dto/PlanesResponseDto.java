package com.example.ticketmasterapi.clients.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlanesResponseDto {
    public List<Flight> data;
}
