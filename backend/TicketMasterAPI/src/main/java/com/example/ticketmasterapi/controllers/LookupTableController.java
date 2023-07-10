package com.example.ticketmasterapi.controllers;

import com.example.ticketmasterapi.clients.aviationstack.AviationStackClient;
import com.example.ticketmasterapi.services.FlightService;
import com.example.ticketmasterapi.services.LookupTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/ticketmaster")
@RequiredArgsConstructor
public class LookupTableController {
    private final LookupTableService lookupTableService;
    @GetMapping("/search")
    public ResponseEntity<?> getFlight(@RequestParam String search) {
        return ResponseEntity.ok(lookupTableService.getAirportsByPartialAirport(search));
    }
}
