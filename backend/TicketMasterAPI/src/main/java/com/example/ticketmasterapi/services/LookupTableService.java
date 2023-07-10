package com.example.ticketmasterapi.services;

import com.example.ticketmasterapi.dto.LookupTableResource;

public interface LookupTableService {

    LookupTableResource save(LookupTableResource lookupTableResource);

    LookupTableResource getCity(String IATA);
}
