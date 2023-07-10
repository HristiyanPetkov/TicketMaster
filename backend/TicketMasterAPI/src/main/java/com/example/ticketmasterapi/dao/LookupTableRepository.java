package com.example.ticketmasterapi.dao;

import com.example.ticketmasterapi.models.LookupTableEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LookupTableRepository {
    String getAirport(String IATA);

    String getIATA(String airport);

    LookupTableEntity save(LookupTableEntity lookupTableEntity);

    List<String> getAirportsByPartialAirport(String partialAirport);
}
