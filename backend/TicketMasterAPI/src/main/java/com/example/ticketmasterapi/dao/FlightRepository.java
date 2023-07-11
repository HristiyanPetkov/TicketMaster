package com.example.ticketmasterapi.dao;


import com.example.ticketmasterapi.models.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    List<FlightEntity> findByArrivalIATAAndDepartureIATAAndArrivalDateAfter(String arrivalIATA, String departureIATA, Timestamp arrivalDate);
    List<FlightEntity> findByArrivalIATAAndDepartureIATAAndDepartureDateAfter(String arrivalIATA, String departureIATA, Timestamp departureDate);
}
