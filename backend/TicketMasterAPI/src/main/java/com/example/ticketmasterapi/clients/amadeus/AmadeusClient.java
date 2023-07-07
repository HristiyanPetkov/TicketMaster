package com.example.ticketmasterapi.clients.amadeus;

import com.example.ticketmasterapi.clients.amadeus.dto.FlightDtoAm;
import com.example.ticketmasterapi.clients.amadeus.dto.FlightsDataAm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AmadeusClient {
    private WebClient webClient;

    @Value("${amadeus.url}")
    private String url;

    public List<FlightDtoAm> getFullFlights(String originLocationCode, String destinationLocationCode, Integer adults, Timestamp departureDate) {
        FlightsDataAm flightsDataAm = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(FlightsDataAm.class)
                .block();
        return flightsDataAm.data;
    }


}
