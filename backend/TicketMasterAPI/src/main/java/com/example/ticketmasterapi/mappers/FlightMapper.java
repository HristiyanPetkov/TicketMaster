package com.example.ticketmasterapi.mappers;

import com.example.ticketmasterapi.clients.aviationstack.dto.Flight;
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

    @Mapping(target = "date", source = "flight.flight_date")
    @Mapping(target = "departureAirport", source = "flight.departure.airport")
    @Mapping(target = "departureIata", source = "flight.departure.iata")
    @Mapping(target = "arrivalAirport", source = "flight.arrival.airport")
    @Mapping(target = "arrivalIata", source = "flight.arrival.iata")
    FlightEntity fromFlight(Flight flight);

    List<FlightResource> toFlightResources(List<FlightEntity> flightList);

    List<FlightEntity> fromFlightResources(List<FlightResource> flightResourceList);

    List<FlightEntity> fromFlights(List<Flight> flights);

}

