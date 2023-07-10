package com.example.ticketmasterapi.dao;

import com.example.ticketmasterapi.models.LookupTableEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface LookupTableRepository {
    LookupTableEntity getCity(String IATA);

    LookupTableEntity save(LookupTableEntity lookupTableEntity);
}
