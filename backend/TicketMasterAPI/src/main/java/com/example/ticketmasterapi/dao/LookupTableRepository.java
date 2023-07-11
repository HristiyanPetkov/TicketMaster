package com.example.ticketmasterapi.dao;

import com.example.ticketmasterapi.models.LookupTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LookupTableRepository extends JpaRepository<LookupTableEntity, Long> {
    LookupTableEntity getLookupTableEntityByIATA(String IATA);

    LookupTableEntity getLookupTableEntityByAirport(String airport);

    List<LookupTableEntity> getAirportsByAirportContaining(String partialAirport);

    boolean existsByAirportAndIATA(String airport, String IATA);
}
