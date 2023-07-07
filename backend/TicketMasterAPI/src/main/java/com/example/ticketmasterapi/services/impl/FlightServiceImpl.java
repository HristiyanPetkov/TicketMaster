package com.example.ticketmasterapi.services.impl;

import com.example.ticketmasterapi.dao.FlightRepository;
import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.services.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import static com.example.ticketmasterapi.mappers.FlightMapper.FLIGHT_MAPPER;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    @Override
    public List<FlightResource> getAll() {
        return FLIGHT_MAPPER.toFlightResources(flightRepository.findAll());
    }

    @Override
    public FlightResource getById(Long id) {
        return FLIGHT_MAPPER.toFlightResource(flightRepository.getReferenceById(id));
    }

    @Override
    public FlightResource save(FlightResource flightResource) {
        return FLIGHT_MAPPER.toFlightResource(flightRepository.save(FLIGHT_MAPPER.fromFlightResource(flightResource)));
    }

    @Override
    public FlightResource update(FlightResource flightResource, Long id) {
        return FLIGHT_MAPPER.toFlightResource(flightRepository.save(FLIGHT_MAPPER.fromFlightResource(flightResource)));
    }

    @Override
    public void delete(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public FlightResource getFlight(String origin, String destination, Timestamp departureDate, Timestamp arrivalDate) {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        long yesterdayInMillis = currentDate.getTime() - (24 * 60 * 60 * 1000);
        Timestamp yesterday = new Timestamp(yesterdayInMillis);
        if(departureDate.after(yesterday)) {
            System.out.println(1);
            List<FlightResource> flights = FLIGHT_MAPPER.toFlightResources(flightRepository.findByArrivalAirportAndDepartureAirportAndDepartureDateAfter(origin, destination, departureDate));
            System.out.println(flights.stream().findFirst().orElse(null));
            return flights.stream().findFirst().orElse(null);
        } else if(arrivalDate.after(yesterday)) {
            System.out.println(2);
            List<FlightResource> flights = FLIGHT_MAPPER.toFlightResources(flightRepository.findByArrivalAirportAndDepartureAirportAndArrivalDateAfter(origin, destination, arrivalDate));
            return flights.stream().findFirst().orElse(null);
        }
        System.out.println(3);
        return null;
    }

    @Override
    public Object addFlight(FlightResource flightResource) {
        return FLIGHT_MAPPER.toFlightResource(flightRepository.save(FLIGHT_MAPPER.fromFlightResource(flightResource)));
    }

}
