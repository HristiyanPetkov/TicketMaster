package com.example.ticketmasterapi.impl;

import com.example.ticketmasterapi.dao.FlightRepository;
import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.example.ticketmasterapi.mappers.FlightMapper.FLIGHT_MAPPER;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    @Override
    public FlightResource getFlight(String origin, String destination, Timestamp date) {
        List<FlightResource> flights = FLIGHT_MAPPER.toFlightResources(flightRepository.findByArrivalAndDepartureAndDateAfter(origin, destination, date));
        return flights.stream().findFirst().orElse(null);
    }

    @Override
    public FlightResource getCheapestDirectFlight(String origin, String destination, Timestamp date) {
        List<FlightResource> flights = FLIGHT_MAPPER.toFlightResources(flightRepository.findByArrivalAndDepartureAndDateAfter(origin, destination, date));
        return flights.stream().min((f1, f2) -> Float.compare(f1.getPrice(), f2.getPrice())).orElse(null);
    }

    @Override
    public FlightResource addFlight(FlightResource flight) {
        return FLIGHT_MAPPER.toFlightResource(flightRepository.save(FLIGHT_MAPPER.fromFlightResource(flight)));
    }
}
