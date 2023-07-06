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
    private Timestamp date;
    private String departure;
    private String arrival;
    private String IATACode;

}
