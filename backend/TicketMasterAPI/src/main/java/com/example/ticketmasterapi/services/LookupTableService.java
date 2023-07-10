package com.example.ticketmasterapi.services;

import com.example.ticketmasterapi.dto.LookupTableResource;
import com.example.ticketmasterapi.models.LookupTableEntity;

import java.util.List;

public interface LookupTableService {

    LookupTableResource save(LookupTableResource lookupTableResource);

    String getAirport(String IATA);

    String getIATA(String airport);

    public List<LookupTableResource> getAirportsByPartialAirport(String partialAirport);
}
