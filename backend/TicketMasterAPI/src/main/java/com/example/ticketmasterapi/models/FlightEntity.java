package com.example.ticketmasterapi.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public void setArrivalArrivalIata(String arrivalIata) {
        this.arrivalIata = arrivalIata;
    }

    public void setArrivalDepartureIata(String departureIata) {
        this.departureIata = departureIata;
    }
}
