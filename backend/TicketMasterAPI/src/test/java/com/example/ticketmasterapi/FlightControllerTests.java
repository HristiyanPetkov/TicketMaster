package com.example.ticketmasterapi;

import com.example.ticketmasterapi.clients.aviationstack.AviationStackClient;
import com.example.ticketmasterapi.controllers.FlightController;
import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.services.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FlightControllerTests {

    private FlightService flightService;
    private FlightController flightController;
    private AviationStackClient aviationStackClient;

    @BeforeEach
    void setUp() {
        flightService = mock(FlightService.class);
        flightController = new FlightController(flightService, aviationStackClient);
        FlightResource flightResource = new FlightResource();
        flightResource.setArrivalAirport("arrivalAirport");
        flightResource.setDepartureAirport("departureAirport");
        flightResource.setArrival_date(Timestamp.valueOf("2023-07-13 06:20:00.000000"));
        flightResource.setDeparture_date(Timestamp.valueOf("2023-07-13 01:45:00.000000"));
        flightResource.setPrice(100f);
        flightService.addFlight(flightResource);

        FlightResource flightResource2 = new FlightResource();
        flightResource2.setArrivalAirport("arrivalAirport");
        flightResource2.setDepartureAirport("departureAirport");
        flightResource2.setArrival_date(Timestamp.valueOf("2023-07-13 07:20:00.000000"));
        flightResource2.setDeparture_date(Timestamp.valueOf("2023-07-13 03:45:00.000000"));
        flightResource2.setPrice(120f);
        flightService.addFlight(flightResource2);
    }

    @Test
    void testAddFlight() {
        FlightResource flightResource = new FlightResource();
        flightResource.setArrivalAirport("Sydney");
        flightResource.setDepartureAirport("Melbourne");
        flightResource.setArrival_date(Timestamp.valueOf("2023-07-13 06:20:00.000000"));
        flightResource.setDeparture_date(Timestamp.valueOf("2023-07-13 01:45:00.000000"));
        flightService.addFlight(flightResource);

        // Perform the test
        ResponseEntity<?> response = flightController.addFlight(flightResource);

        // Verify the results
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetFlight() {
        ResponseEntity<?> response = flightController.getFlight("arrivalAirport", "departureAirport", "2023-07-13 06:20:00.000000", "2023-07-13 01:45:00.000000");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetCheapestDirectFlight() {
        FlightResource resource = flightService.getCheapestDirectFlight("arrivalAirport", "departureAirport", Timestamp.valueOf("2023-07-13 01:45:00.000000"), Timestamp.valueOf("2023-07-13 07:20:00.000000"));
        assertEquals(100f, resource.getPrice());
    }
}
