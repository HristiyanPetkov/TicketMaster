package com.example.ticketmasterapi.services.impl;

import com.example.ticketmasterapi.dao.LookupTableRepository;
import com.example.ticketmasterapi.dto.LookupTableResource;
import com.example.ticketmasterapi.services.LookupTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public LookupTableResource getCity(String IATA) {
        return LOOKUP_TABLE_MAPPER.toLookupTableResource(lookupTableRepository.getCity(IATA));
    }
}
