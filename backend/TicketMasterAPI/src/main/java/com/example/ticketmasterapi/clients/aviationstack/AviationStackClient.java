package com.example.ticketmasterapi.clients.aviationstack;

import com.example.ticketmasterapi.clients.aviationstack.dto.FlightDto;
import com.example.ticketmasterapi.clients.aviationstack.dto.FlightsData;
import com.example.ticketmasterapi.clients.skyscanner.SkyscannerClient;
import com.example.ticketmasterapi.clients.skyscanner.dto.FlightDtoSc;
import com.example.ticketmasterapi.dao.FlightRepository;
import com.example.ticketmasterapi.models.FlightEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;

import static com.example.ticketmasterapi.mappers.FlightMapper.FLIGHT_MAPPER;

@Service
@RequiredArgsConstructor
public class AviationStackClient {
    private final FlightRepository flightRepository;

    private final WebClient webClient;

    private final SkyscannerClient skyscannerClient;

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

        for (FlightEntity flight : flights) {
            String origin = flight.getDepartureIata();
            String destination = flight.getArrivalIata();
            String date = flight.getDepartureDate().toString();
            Collection<FlightDtoSc> response = skyscannerClient.getFlightsPrice(origin, destination, date);
            if (response.isEmpty()) {
                continue;
            }
            flight.setPrice(response.iterator().next().minPrice.amount);
            //System.out.println(response);
        }

        flightRepository.saveAll(flights);

        System.out.println("Saved flights!");
    }
}
