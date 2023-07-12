package com.example.ticketmasterapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class FlightEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String flightNumber;
    private Timestamp departureDate;
    private Timestamp arrivalDate;
    private String departureIata;
    private String arrivalIata;
    private String airline;
    private Float price;
}
