package com.example.ticketmasterapi.mappers;

import com.example.ticketmasterapi.clients.aviationstack.dto.FlightDto;
import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.models.FlightEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FlightMapper {
    FlightMapper FLIGHT_MAPPER = Mappers.getMapper(FlightMapper.class);

    FlightResource toFlightResource(FlightEntity flight);

    FlightEntity fromFlightResource(FlightResource flightResource);

    @Mapping(target = "date", source = "flightDto.flight_date")
    @Mapping(target = "departureAirport", source = "flightDto.departure.airport")
    @Mapping(target = "departureIata", source = "flightDto.departure.iata")
    @Mapping(target = "arrivalAirport", source = "flightDto.arrival.airport")
    @Mapping(target = "arrivalIata", source = "flightDto.arrival.iata")
    @Mapping(target = "flightNumber", source = "flightDto.flight.number")
    FlightEntity fromFlight(FlightDto flightDto);

    List<FlightResource> toFlightResources(List<FlightEntity> flightList);

    List<FlightEntity> fromFlightResources(List<FlightResource> flightResourceList);

    List<FlightEntity> fromFlights(List<FlightDto> flightDtos);

}

