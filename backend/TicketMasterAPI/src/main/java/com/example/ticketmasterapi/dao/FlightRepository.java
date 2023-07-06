package com.example.ticketmasterapi.dao;


import com.example.ticketmasterapi.models.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
    List<FlightEntity> findByArrivalAndDepartureAndDate(String arrival, String departure, Timestamp date);

    List<FlightEntity> findByArrivalAndDate(String arrival, Timestamp date);
}
