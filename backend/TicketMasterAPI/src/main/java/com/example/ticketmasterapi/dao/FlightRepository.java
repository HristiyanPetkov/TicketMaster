package com.example.ticketmasterapi.dao;


import com.example.ticketmasterapi.models.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    List<FlightEntity> findByArrivalIataAndDepartureIataAndDepartureDateAfter(String arrivalIATA, String departureIATA, Timestamp departureDate);
    List<FlightEntity> findByDepartureIataAndDepartureDateAfter(String departureIATA, Timestamp departureDate);
    List<FlightEntity> findByDepartureIataAndArrivalDateAfter(String departureIATA, Timestamp arrivalDate);
    List<FlightEntity> findByArrivalIataAndDepartureDateAfter(String arrivalIATA, Timestamp departureDate);
    List<FlightEntity> findByArrivalIataAndArrivalDateAfter(String arrivalIATA, Timestamp arrivalDate);
    List<FlightEntity> findByArrivalIataAndDepartureIataAndArrivalDateAfter(String arrivalIATA, String departureIATA, Timestamp arrivalDate);
    FlightEntity findByFlightNumberAndDepartureDateAfter(String flightNumber, Timestamp departureDate);
    FlightEntity findByFlightNumberAndArrivalDateAfter(String flightNumber, Timestamp arrivalDate);
    List<FlightEntity> findByFlightNumber(String flightNumber);
}
