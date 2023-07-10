package com.example.ticketmasterapi.clients.aviationstack;

import com.example.ticketmasterapi.clients.amadeus.AmadeusClient;
import com.example.ticketmasterapi.clients.amadeus.dto.FlightDtoAm;
import com.example.ticketmasterapi.clients.aviationstack.dto.FlightDto;
import com.example.ticketmasterapi.clients.aviationstack.dto.FlightsData;
import com.example.ticketmasterapi.dao.FlightRepository;
import com.example.ticketmasterapi.dao.LookupTableRepository;
import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.dto.LookupTableResource;
import com.example.ticketmasterapi.models.FlightEntity;
import com.example.ticketmasterapi.services.LookupTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static com.example.ticketmasterapi.mappers.FlightMapper.FLIGHT_MAPPER;
import static com.example.ticketmasterapi.mappers.LookupTableMapper.LOOKUP_TABLE_MAPPER;

@Service
@RequiredArgsConstructor
public class AviationStackClient {
    private final FlightRepository flightRepository;

    private final LookupTableRepository lookupTableRepository;

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

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    public void saveFlights() {
        List<FlightDto> flightsDto = getFlights();
        List<FlightEntity> flights = FLIGHT_MAPPER.fromFlights(flightsDto);
        flightRepository.saveAll(flights);
        addAirports(flightsDto);

        System.out.println("Saved flights!");
    }

    public void addAirports(List<FlightDto> flights){
        for(FlightDto flightDto : flights) {
            lookupTableRepository.save(LOOKUP_TABLE_MAPPER.fromFLightDtoByDeparture(flightDto));
            lookupTableRepository.save(LOOKUP_TABLE_MAPPER.fromFLightDtoByArrival(flightDto));
        }
    }
}
