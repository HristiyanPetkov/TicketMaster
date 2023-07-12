package com.example.ticketmasterapi.services.impl;

import com.example.ticketmasterapi.dao.FlightRepository;
import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.models.FlightEntity;
import com.example.ticketmasterapi.services.FlightService;
import com.example.ticketmasterapi.services.LookupTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.example.ticketmasterapi.mappers.FlightMapper.FLIGHT_MAPPER;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    private final LookupTableService lookupTableService;

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
        String originIATA = lookupTableService.getIATA(origin);     //we are gonan work with IATA codes on entity and airport name on resource layer. this simply converts the airport name to IATA code.
        String destinationIATA = lookupTableService.getIATA(destination);
        if(departureDate.after(yesterday)) {
            System.out.println(1);
            List<FlightEntity> tempFLights = flightRepository.findByArrivalIataAndDepartureIataAndDepartureDateAfter(originIATA, destinationIATA, departureDate);   //clarity
            return getDesiredFLight(tempFLights, origin, destination);
        } else if(arrivalDate.after(yesterday)) {
            System.out.println(2);
            List<FlightEntity> tempFlights = flightRepository.findByArrivalIataAndDepartureIataAndArrivalDateAfter(originIATA, destinationIATA, arrivalDate);
            return getDesiredFLight(tempFlights, origin, destination);
        }
        System.out.println(3);
        return null;
    }

    @Override
    public Object addFlight(FlightResource flightResource) {
        System.out.println(FLIGHT_MAPPER.fromFlightResource(flightResource));
        return FLIGHT_MAPPER.toFlightResource(flightRepository.save(FLIGHT_MAPPER.fromFlightResource(flightResource)));
    }

    private FlightResource getDesiredFLight(List<FlightEntity> tempFlights, String origin, String destination){
        List<FlightResource> flights = FLIGHT_MAPPER.toFlightResources(tempFlights);
        System.out.println(flights.stream().findFirst().orElse(null));
        FlightResource result = flights.stream().findFirst().orElse(null);
        if(result != null) {
            result.setDepartureAirport(origin);
            result.setArrivalAirport(destination);
        }
        return result;
    }
}
