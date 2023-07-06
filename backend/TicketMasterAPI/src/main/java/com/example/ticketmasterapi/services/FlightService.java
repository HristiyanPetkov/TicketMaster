package com.example.ticketmasterapi.services;
import com.example.ticketmasterapi.dto.FlightResource;

import java.sql.Timestamp;

public interface FlightService {
    FlightResource getFlight(String origin, String destination, Timestamp date);
    FlightResource getCheapestDirectFlight(String origin, String destination, Timestamp date);

    FlightResource addFlight(FlightResource flight);
}
