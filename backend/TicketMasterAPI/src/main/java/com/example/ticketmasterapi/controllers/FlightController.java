package com.example.ticketmasterapi.controllers;

import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.clients.aviationstack.AviationStackClient;
import com.example.ticketmasterapi.clients.aviationstack.dto.FlightDto;
import com.example.ticketmasterapi.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/ticketmaster")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
    private final AviationStackClient aviationStackClient;
    @GetMapping("/flights")
    public ResponseEntity<?> getFlights() {
        List<FlightDto> flightsDto = aviationStackClient.getFlights();
        return ResponseEntity.ok(flightsDto);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getFlight(@RequestParam String arrivalAirport, @RequestParam String departureAirport, @RequestParam String arrival_date, @RequestParam String departure_date) {
        return ResponseEntity.ok(flightService.getFlight(departureAirport, arrivalAirport, Timestamp.valueOf(departure_date.replaceAll("[a-zA-Z]", " ")), Timestamp.valueOf(arrival_date.replaceAll("[a-zA-Z]", " "))));
    }

    @GetMapping("/getCheapestDirect")
    public ResponseEntity<?> getCheapestDirectFlight(@RequestParam String arrivalAirport, @RequestParam String departureAirport, @RequestParam String arrival_date, @RequestParam String departure_date) {
        return ResponseEntity.ok(flightService.getCheapestDirectFlight(departureAirport, arrivalAirport, Timestamp.valueOf(departure_date.replaceAll("[a-zA-Z]", " ")), Timestamp.valueOf(arrival_date.replaceAll("[a-zA-Z]", " "))));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFlight(@RequestBody FlightResource flightResource) {
        System.out.println(flightResource);
        return ResponseEntity.ok(flightService.addFlight(flightResource));
    }
}