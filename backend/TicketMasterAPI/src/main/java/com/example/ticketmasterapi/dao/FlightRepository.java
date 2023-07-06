package com.example.ticketmasterapi.dao;


import com.example.ticketmasterapi.models.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<FlightEntity, Long> {
}
