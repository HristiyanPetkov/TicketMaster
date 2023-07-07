package com.example.ticketmasterapi.clients.aviationstack;

import com.example.ticketmasterapi.clients.aviationstack.dto.Flight;
import com.example.ticketmasterapi.clients.aviationstack.dto.FlightsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AviationStackConnection {

    private final WebClient webClient;
    private final String url = "http://api.aviationstack.com/v1/flights?access_key=199e856ff52db9f13544a3d172b6c01c&flight_status=scheduled";
    public List<Flight> getFlights() {
        FlightsResponseDto flights  = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(FlightsResponseDto.class)
                .block();
        return flights.data;
    }
}
