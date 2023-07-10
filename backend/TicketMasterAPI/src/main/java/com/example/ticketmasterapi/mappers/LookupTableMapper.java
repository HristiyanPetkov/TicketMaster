package com.example.ticketmasterapi.mappers;

import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.dto.LookupTableResource;
import com.example.ticketmasterapi.models.LookupTableEntity;
import com.example.ticketmasterapi.clients.aviationstack.dto.FlightDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LookupTableMapper {
    LookupTableMapper LOOKUP_TABLE_MAPPER = Mappers.getMapper(LookupTableMapper.class);

    LookupTableResource toLookupTableResource(LookupTableEntity lookupTableEntity);

    LookupTableEntity fromLookupTableResource(LookupTableResource lookupTableResource);

    List<LookupTableResource> toLookupTableResources(List<LookupTableEntity> lookupTableEntityList);

    List<LookupTableEntity> fromLookupTableResources(List<LookupTableResource> lookupTableResourceList);

    @Mapping(target = "IATA", source = "flightDto.departure.iata")
    @Mapping(target = "airport", source = "flightDto.departure.airport")
    LookupTableEntity fromFLightDtoByDeparture(FlightDto flightDto);

    @Mapping(target = "IATA", source = "flightDto.arrival.iata")
    @Mapping(target = "airport", source = "flightDto.arrival.airport")
    LookupTableEntity fromFLightDtoByArrival(FlightDto flightDto);
}
