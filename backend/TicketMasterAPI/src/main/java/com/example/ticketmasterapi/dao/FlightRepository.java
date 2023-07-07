package com.example.ticketmasterapi.dao;


import com.example.ticketmasterapi.models.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    List<FlightEntity> findByArrivalAirportAndDepartureAirportAndArrivalDateAfter(String arrivalAirport, String departureAirport, Timestamp arrivalDate);
    List<FlightEntity> findByArrivalAirportAndDepartureAirportAndDepartureDateAfter(String arrivalAirport, String departureAirport, Timestamp departureDate);
}
