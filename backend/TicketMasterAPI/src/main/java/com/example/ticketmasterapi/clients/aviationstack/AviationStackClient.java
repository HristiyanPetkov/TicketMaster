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

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
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
        }


        Date currentDate = new Date(System.currentTimeMillis());
        Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
        Timestamp tomorrowTimestamp = new Timestamp(currentDate.getTime() + 86400000);

        FlightEntity tempFlight1 = new FlightEntity(101L, "10000", currentTimestamp , tomorrowTimestamp, "SOF", "VAR", "British Airways", 10000f);
        FlightEntity tempFlight2 = new FlightEntity(102L, "10001", currentTimestamp , tomorrowTimestamp, "SOF", "VAR", "Qatar Airways", 9000f);
        FlightEntity tempFlight3 = new FlightEntity(103L, "10001", currentTimestamp , tomorrowTimestamp, "VAR", "BKK", "Qatar Airways", 9000f);
        flights.add(tempFlight1);
        flights.add(tempFlight2);
        flights.add(tempFlight3);
        flightRepository.saveAll(removeIncompleteFlights(flights));
        System.out.println(tempFlight3);
        FlightDto tempFlightDto1 = FLIGHT_MAPPER.toFlight(tempFlight1);
        FlightDto tempFlightDto2 = FLIGHT_MAPPER.toFlight(tempFlight2);
        FlightDto tempFlightDto3 = FLIGHT_MAPPER.toFlight(tempFlight3);
        tempFlightDto1.departure.airport = "Sofia Airport";
        tempFlightDto1.arrival.airport = "Varna Airport";
        tempFlightDto2.departure.airport = "Sofia Airport";
        tempFlightDto2.arrival.airport = "Varna Airport";
        tempFlightDto3.departure.airport = "Varna Airport";
        tempFlightDto3.arrival.airport = "Bangkok Airport";
        flightsDto.add(tempFlightDto1);
        flightsDto.add(tempFlightDto2);
        flightsDto.add(tempFlightDto3);
        addAirports(flightsDto);

        System.out.println("Saved flights!");
    }

    private void addAirports(List<FlightDto> flights){
        for(FlightDto flightDto : flights) {
            if(validAirport(flightDto.departure.airport, flightDto.departure.iata))
                lookupTableRepository.save(LOOKUP_TABLE_MAPPER.fromFLightDtoByDeparture(flightDto));

            if(validAirport(flightDto.arrival.airport, flightDto.arrival.iata))
                lookupTableRepository.save(LOOKUP_TABLE_MAPPER.fromFLightDtoByArrival(flightDto));
        }
    }

    private List<FlightEntity> removeIncompleteFlights(List<FlightEntity> flights) {
        flights.removeIf(flight -> flight.getDepartureIata() == null || flight.getArrivalIata() == null || flight.getPrice() == null);
        return flights;
    }

    private boolean validAirport(String airport, String iata) {
        return !lookupTableRepository.existsByAirportAndIATA(airport, iata) && airport != null && !airport.isEmpty() && iata != null && !iata.isEmpty();
    }
}
