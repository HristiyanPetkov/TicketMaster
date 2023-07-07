package com.example.ticketmasterapi.clients.aviationstack;

import com.example.ticketmasterapi.clients.amadeus.AmadeusClient;
import com.example.ticketmasterapi.clients.amadeus.dto.FlightDtoAm;
import com.example.ticketmasterapi.clients.aviationstack.dto.FlightDto;
import com.example.ticketmasterapi.clients.aviationstack.dto.FlightsData;
import com.example.ticketmasterapi.dao.FlightRepository;
import com.example.ticketmasterapi.models.FlightEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static com.example.ticketmasterapi.mappers.FlightMapper.FLIGHT_MAPPER;

@Service
@RequiredArgsConstructor
public class AviationStackClient {
    private final FlightRepository flightRepository;

    private final AmadeusClient amadeusClient;

    private final WebClient webClient;

    @Value("${aviationstack.url}")
    private String url;
    public List<FlightDto> getFlights() {
        FlightsData flights  = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(FlightsData.class)
                .block();
        return flights.data;
    }

    // FOR ACTUAL USE: @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void saveFlights() {
        List<FlightDto> flightsDto = getFlights();
        List<FlightEntity> flights = FLIGHT_MAPPER.fromFlights(flightsDto);
        flightRepository.saveAll(flights);

        System.out.println("Saved flights!");
    }
}
