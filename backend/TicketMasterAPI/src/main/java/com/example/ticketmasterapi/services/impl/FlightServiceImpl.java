package com.example.ticketmasterapi.services.impl;

import com.example.ticketmasterapi.clients.aviationstack.dto.Flight;
import com.example.ticketmasterapi.dao.FlightRepository;
import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.models.FlightEntity;
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
    public List<FlightResource> getAll() {
        return FLIGHT_MAPPER.toFlightResources(flightRepository.findAll());
    }

    @Override
    public FlightResource getById(Long id) {
        return FLIGHT_MAPPER.toFlightResource(flightRepository.getReferenceById(id));
    }

    @Override
    public FlightResource save(FlightResource flightResource) {
        return FLIGHT_MAPPER.toFlightResource(flightRepository.save(FLIGHT_MAPPER.fromFlightResource(flightResource)));
    }

    @Override
    public FlightResource update(FlightResource flightResource, Long id) {
        return FLIGHT_MAPPER.toFlightResource(flightRepository.save(FLIGHT_MAPPER.fromFlightResource(flightResource)));
    }

    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public FlightResource getFlight(String origin, String destination, Timestamp date) {
        List<FlightResource> flights = FLIGHT_MAPPER.toFlightResources(flightRepository.findByArrivalAirportAndDepartureAirportAndDateAfter(origin, destination, date));
        return flights.stream().findFirst().orElse(null);
    }

}
