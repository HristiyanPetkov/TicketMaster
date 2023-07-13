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
    @Mapping(target = "departureAirport", source = "flight.departureIata")
    @Mapping(target = "arrivalAirport", source = "flight.arrivalIata")
    @Mapping(target = "flightNumber", source = "flight.flightNumber")
    @Mapping(target = "price", source = "flight.price")
    @Mapping(target = "airline", source = "flight.airline")
    FlightResource toFlightResource(FlightEntity flight);

    @Mapping(target = "arrivalDate", source =    "flightResource.arrival_date")
    @Mapping(target = "departureDate", source = "flightResource.departure_date")
    @Mapping(target = "departureIata", source = "flightResource.departureAirport")
    @Mapping(target = "arrivalIata", source = "flightResource.arrivalAirport")
    @Mapping(target = "flightNumber", source = "flightResource.flightNumber")
    @Mapping(target = "price", source = "flightResource.price")
    @Mapping(target = "airline", source = "flightResource.airline")
    FlightEntity fromFlightResource(FlightResource flightResource);

    @Mapping(target = "departureDate", source = "flightDto.departure.scheduled")
    @Mapping(target = "arrivalDate", source = "flightDto.arrival.scheduled")
    @Mapping(target = "departureIata", source = "flightDto.departure.iata")
    @Mapping(target = "arrivalIata", source = "flightDto.arrival.iata")
    @Mapping(target = "flightNumber", source = "flightDto.flight.number")
    @Mapping(target = "airline", source = "flightDto.airline.name")
    FlightEntity fromFlight(FlightDto flightDto);

    @Mapping(target = "departure.scheduled", source = "flightEntity.departureDate")
    @Mapping(target = "arrival.scheduled", source = "flightEntity.arrivalDate")
    @Mapping(target = "departure.iata", source = "flightEntity.departureIata")
    @Mapping(target = "arrival.iata", source = "flightEntity.arrivalIata")
    @Mapping(target = "flight.number", source = "flightEntity.flightNumber")
    @Mapping(target = "airline.name", source = "flightEntity.airline")
    FlightDto toFlight(FlightEntity flightEntity);

    List<FlightResource> toFlightResources(List<FlightEntity> flightList);

    List<FlightEntity> fromFlightResources(List<FlightResource> flightResourceList);

    List<FlightEntity> fromFlights(List<FlightDto> flightDtos);

}

