package com.example.ticketmasterapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class FlightEntity {
    @Id
    @GeneratedValue
    private Long id;
}
