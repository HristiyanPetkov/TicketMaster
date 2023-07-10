package com.example.ticketmasterapi.services;

import com.example.ticketmasterapi.dto.LookupTableResource;

import java.util.List;

public interface LookupTableService {

    LookupTableResource save(LookupTableResource lookupTableResource);

    String getAirport(String IATA);

    String getIATA(String airport);

    List<String> getAirportsByPartialAirport(String partialAirport);
}
