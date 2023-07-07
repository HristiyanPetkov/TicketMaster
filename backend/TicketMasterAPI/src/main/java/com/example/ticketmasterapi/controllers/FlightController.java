package com.example.ticketmasterapi.controllers;

import com.example.ticketmasterapi.clients.aviationstack.AviationStackConnection;
import com.example.ticketmasterapi.clients.aviationstack.dto.Flight;
import com.example.ticketmasterapi.dao.FlightRepository;
import com.example.ticketmasterapi.models.FlightEntity;
import com.example.ticketmasterapi.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

import static com.example.ticketmasterapi.mappers.FlightMapper.FLIGHT_MAPPER;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/ticketmaster")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;
    private final FlightRepository flightRepository;
    private final AviationStackConnection aviationStackConnection;
    @GetMapping("/flights")
    public ResponseEntity<?> getFlights() {
        List<Flight> flightsDto = aviationStackConnection.getFlights();
        List<FlightEntity> flights = FLIGHT_MAPPER.fromFlights(flightsDto);
        flightRepository.saveAll(flights);
        return ResponseEntity.ok(flightsDto);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getFlight(@RequestParam String arrival, @RequestParam String departure, @RequestParam String date) {
        return ResponseEntity.ok(flightService.getFlight(arrival, departure, Timestamp.valueOf(date.replaceAll("[a-zA-Z]", " "))));
    }

//    @GetMapping("/getCheapestDirect")
//    public ResponseEntity<?> getCheapestDirectFlight(@RequestBody FlightResource flightResource) {
//        return ResponseEntity.ok(flightService.getCheapestDirectFlight(flightResource.getArrival(), flightResource.getDeparture(), flightResource.getDate()));
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<?> addFlight(@RequestBody FlightResource flightResource) {
//        return ResponseEntity.ok(flightService.addFlight(flightResource));
//    }
}