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

    @Mapping(target = "arrival_date", source = "flight.arrivalDate")
    @Mapping(target = "departure_date", source = "flight.departureDate")
    @Mapping(target = "departureAirport", source = "flight.departureIATA")
    @Mapping(target = "arrivalAirport", source = "flight.arrivalIATA")
    @Mapping(target = "flightNumber", source = "flight.flightNumber")
    @Mapping(target = "price", source = "flight.price")
    FlightResource toFlightResource(FlightEntity flight);

    @Mapping(target = "arrivalDate", source =    "flightResource.arrival_date")
    @Mapping(target = "departureDate", source = "flightResource.departure_date")
    @Mapping(target = "departureIATA", source = "flightResource.departureAirport")
    @Mapping(target = "arrivalIATA", source = "flightResource.arrivalAirport")
    @Mapping(target = "flightNumber", source = "flightResource.flightNumber")
    @Mapping(target = "price", source = "flightResource.price")
    FlightEntity fromFlightResource(FlightResource flightResource);

    @Mapping(target = "departureDate", source = "flightDto.departure.scheduled")
    @Mapping(target = "arrivalDate", source = "flightDto.arrival.scheduled")
    @Mapping(target = "departureIATA", source = "flightDto.departure.iata")
    @Mapping(target = "arrivalIATA", source = "flightDto.arrival.iata")
    @Mapping(target = "flightNumber", source = "flightDto.flight.number")
    @Mapping(target = "airline", source = "flightDto.airline.name")
    FlightEntity fromFlight(FlightDto flightDto);

    List<FlightResource> toFlightResources(List<FlightEntity> flightList);

    List<FlightEntity> fromFlightResources(List<FlightResource> flightResourceList);

    List<FlightEntity> fromFlights(List<FlightDto> flightDtos);

}

