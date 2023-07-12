package com.example.ticketmasterapi.clients.aviationstack;

import com.example.ticketmasterapi.clients.aviationstack.dto.FlightDto;
import com.example.ticketmasterapi.clients.aviationstack.dto.FlightsData;
import com.example.ticketmasterapi.clients.skyscanner.SkyscannerClient;
import com.example.ticketmasterapi.clients.skyscanner.dto.Carrier;
import com.example.ticketmasterapi.clients.skyscanner.dto.FlightDtoSc;
import com.example.ticketmasterapi.clients.skyscanner.dto.Result;
import com.example.ticketmasterapi.dao.FlightRepository;
import com.example.ticketmasterapi.dao.LookupTableRepository;
import com.example.ticketmasterapi.models.FlightEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.List;

import static com.example.ticketmasterapi.mappers.FlightMapper.FLIGHT_MAPPER;
import static com.example.ticketmasterapi.mappers.LookupTableMapper.LOOKUP_TABLE_MAPPER;

@Service
@RequiredArgsConstructor
public class AviationStackClient {
    private final FlightRepository flightRepository;

    private final LookupTableRepository lookupTableRepository;

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

        assert flights != null;
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
            Result result = skyscannerClient.getFlightsPrice(origin, destination, date);
            Collection<FlightDtoSc> flightsPrices = result.quotes.values();
            if (flightsPrices.isEmpty()) {
                continue;
            }

            for (Carrier carrier : result.carriers.values()) {
                if (carrier.name.equals(flight.getAirline())) {
                    for (FlightDtoSc flightDto : flightsPrices) {
                        BidiMap<String, Carrier> carriers = new DualHashBidiMap<>(result.carriers);
                        if (flightDto.outboundLeg.marketingCarrierId.equals(carriers.getKey(carrier))) {
                            flight.setPrice(flightDto.minPrice.amount);
                            break;
                        }
                    }
                }
            }

            if (flight.getPrice() == null) {
                flight.setPrice(1F);
            }
        }

        flightRepository.saveAll(removeIncompleteFlights(flights));
        addAirports(flightsDto);

        System.out.println("Saved flights!");
    }

    private void addAirports(List<FlightDto> flights){
        for(FlightDto flightDto : flights) {
            if(!lookupTableRepository.existsByAirportAndIATA(flightDto.departure.airport, flightDto.departure.iata))
                lookupTableRepository.save(LOOKUP_TABLE_MAPPER.fromFLightDtoByDeparture(flightDto));

            if(!lookupTableRepository.existsByAirportAndIATA(flightDto.arrival.airport, flightDto.arrival.iata))
                lookupTableRepository.save(LOOKUP_TABLE_MAPPER.fromFLightDtoByArrival(flightDto));
        }
    }

    private List<FlightEntity> removeIncompleteFlights(List<FlightEntity> flights) {
        flights.removeIf(flight -> flight.getDepartureIata() == null || flight.getArrivalIata() == null || flight.getPrice() == null);
        return flights;
    }
}
