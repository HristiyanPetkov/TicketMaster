package com.example.ticketmasterapi.mappers;

import com.example.ticketmasterapi.clients.aviationstack.dto.Flight;
import com.example.ticketmasterapi.clients.amadeus.dto.FlightDtoAm;
import com.example.ticketmasterapi.clients.aviationstack.dto.Flight;
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
    FlightEntity fromFlight(FlightDto flightDto);

    List<FlightResource> toFlightResources(List<FlightEntity> flightList);

    List<FlightEntity> fromFlightResources(List<FlightResource> flightResourceList);

    List<FlightEntity> fromFlights(List<FlightDto> flightDtos);

}

