package com.example.ticketmasterapi.clients;

import com.example.ticketmasterapi.clients.dto.Flight;
import com.example.ticketmasterapi.clients.dto.PlanesResponseDto;
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
        PlanesResponseDto responseDto  = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(PlanesResponseDto.class)
                .block();
        return responseDto.data;
    }
}
