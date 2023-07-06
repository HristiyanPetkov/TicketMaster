package com.example.ticketmasterapi.controllers;

import com.example.ticketmasterapi.clients.AviationStackConnection;
import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.sql.Timestamp;

@RestController
@RequestMapping("/api/v1/ticketmaster")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
    private final AviationStackConnection aviationStackConnection;
    @GetMapping
    public ResponseEntity<?> getFlights() {
        return ResponseEntity.ok(aviationStackConnection.getFlights());
    }
    @GetMapping("/get")
    public ResponseEntity<?> getFlight(@RequestParam String arrival, @RequestParam String departure, @RequestParam String date) {
        return ResponseEntity.ok(flightService.getFlight(arrival, departure, Timestamp.valueOf(date.replaceAll("[a-zA-Z]", " "))));
    }

    @GetMapping("/getCheapestDirect")
    public ResponseEntity<?> getCheapestDirectFlight(@RequestBody FlightResource flightResource) {
        return ResponseEntity.ok(flightService.getCheapestDirectFlight(flightResource.getArrival(), flightResource.getDeparture(), flightResource.getDate()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFlight(@RequestBody FlightResource flightResource) {
        return ResponseEntity.ok(flightService.addFlight(flightResource));
    }
}