package com.example.ticketmasterapi.services;
import com.example.ticketmasterapi.clients.aviationstack.dto.Flight;
import com.example.ticketmasterapi.clients.aviationstack.dto.FlightsResponseDto;
import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.models.FlightEntity;

import java.sql.Timestamp;
import java.util.List;

public interface FlightService {
    List<FlightResource> getAll();
    FlightResource getById(Long id);
    FlightResource save(FlightResource flightResource);
    FlightResource update(FlightResource flightResource, Long id);
    void delete(Long id);
    FlightResource getFlight(String origin, String destination, Timestamp date);
}
