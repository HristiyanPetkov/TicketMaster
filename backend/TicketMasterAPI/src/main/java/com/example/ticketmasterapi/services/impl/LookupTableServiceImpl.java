package com.example.ticketmasterapi.services.impl;

import com.example.ticketmasterapi.dao.LookupTableRepository;
import com.example.ticketmasterapi.dto.LookupTableResource;
import com.example.ticketmasterapi.models.LookupTableEntity;
import com.example.ticketmasterapi.services.LookupTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.ticketmasterapi.mappers.LookupTableMapper.LOOKUP_TABLE_MAPPER;

@Service
@RequiredArgsConstructor
public class LookupTableServiceImpl implements LookupTableService {
    private final LookupTableRepository lookupTableRepository;


    @Override
    public LookupTableResource save(LookupTableResource lookupTableResource) {
        return LOOKUP_TABLE_MAPPER.toLookupTableResource(lookupTableRepository.save(LOOKUP_TABLE_MAPPER.fromLookupTableResource(lookupTableResource)));
    }

    @Override
    public String getAirport(String IATA) {
        return lookupTableRepository.getLookupTableEntityByIATA(IATA).getAirport();
    }

    @Override
    public String getIATA(String airport) {
        return lookupTableRepository.getLookupTableEntityByAirport(airport).getIATA();
    }

    @Override
    public List<String> getAirportsByPartialAirport(String partialAirport) {
        List<LookupTableEntity> lookupTableRepositoryList = lookupTableRepository.getAirportsByAirportContaining(partialAirport);
        return LOOKUP_TABLE_MAPPER.toLookupTableResources(lookupTableRepositoryList).stream().map(LookupTableResource::getAirport).toList();
    }
}
