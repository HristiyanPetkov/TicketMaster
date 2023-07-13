package com.example.ticketmasterapi.services.impl;

import com.example.ticketmasterapi.dao.FlightRepository;
import com.example.ticketmasterapi.dao.LookupTableRepository;
import com.example.ticketmasterapi.dto.FlightResource;
import com.example.ticketmasterapi.models.FlightEntity;
import com.example.ticketmasterapi.services.FlightService;
import com.example.ticketmasterapi.services.LookupTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.ticketmasterapi.mappers.FlightMapper.FLIGHT_MAPPER;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;

    private final LookupTableService lookupTableService;

    private final LookupTableRepository lookupTableRepository;

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
        String originIATA = lookupTableService.getIATA(origin);
        String destinationIATA = lookupTableService.getIATA(destination);
        if(departureDate.after(yesterday)) {
            System.out.println(1);
            List<FlightEntity> tempFLights = flightRepository.findByArrivalIataAndDepartureIataAndDepartureDateAfter(originIATA, destinationIATA, departureDate);
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
    public FlightResource getCheapestDirectFlight(String origin, String destination, Timestamp departureDate, Timestamp arrivalDate) {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        long yesterdayInMillis = currentDate.getTime() - (24 * 60 * 60 * 1000);
        Timestamp yesterday = new Timestamp(yesterdayInMillis);
        String originIATA = lookupTableService.getIATA(origin);     //we are gonan work with IATA codes on entity and airport name on resource layer. this simply converts the airport name to IATA code.
        String destinationIATA = lookupTableService.getIATA(destination);
        if(departureDate.after(yesterday)) {
            System.out.println(1);
            List<FlightEntity> tempFlights = flightRepository.findByArrivalIataAndDepartureIataAndDepartureDateAfter(destinationIATA, originIATA, departureDate);   //clarity
            return getDesiredCheapestDirectFlight(tempFlights, origin, destination);
        } else if(arrivalDate.after(yesterday)) {
            System.out.println(2);
            List<FlightEntity> tempFlights = flightRepository.findByArrivalIataAndDepartureIataAndArrivalDateAfter(destinationIATA, originIATA, arrivalDate);
            return getDesiredCheapestDirectFlight(tempFlights, origin, destination);
        }
        System.out.println(3);
        return null;
    }

    @Override
    public FlightResource getCheapestFlight(String origin, String destination, Timestamp departureDate, Timestamp arrivalDate) {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        long yesterdayInMillis = currentDate.getTime() - (24 * 60 * 60 * 1000);
        Timestamp yesterday = new Timestamp(yesterdayInMillis);
        String originIATA = lookupTableService.getIATA(origin);
        String destinationIATA = lookupTableService.getIATA(destination);
        if(departureDate.after(yesterday)) {
            System.out.println(1);
            return getCheapestFlightWithStops(departureDate, originIATA, destinationIATA);
        } else if(arrivalDate.after(yesterday)) {
            System.out.println(2);
            return getCheapestFlightWithStops(arrivalDate, originIATA, destinationIATA);
        }

        System.out.println(3);
        return null;
    }

    private FlightResource getCheapestFlightWithStops(Timestamp departureDate, String originIATA, String destinationIATA) {
        List<FlightEntity> departureFlights = flightRepository.findByDepartureIataAndArrivalDateAfter(originIATA, departureDate);
        List<FlightEntity> arrivalFlights = flightRepository.findByArrivalIataAndArrivalDateAfter(destinationIATA, departureDate);
        List<FlightResource> resultFlights = new ArrayList<>();
        for(FlightEntity depFlight : departureFlights) {
            arrivalFlights.stream().filter(arrivalFlight -> arrivalFlight.getFlightNumber().equals(depFlight.getFlightNumber())).forEach(arrivalFlight -> {
                resultFlights.add(buildFlight(depFlight.getFlightNumber()));
            });
        }
        return resultFlights.stream().min(Comparator.comparing(FlightResource::getPrice)).orElse(null);
    }

    private FlightResource buildFlight(String FlightNumber) {
        Set<FlightEntity> flights = new HashSet<>(flightRepository.findByFlightNumber(FlightNumber));
        FlightResource flight = FLIGHT_MAPPER.toFlightResource(flights.iterator().next());
        flight.setArrivalAirport(lookupTableRepository.getByIATA(flight.getArrivalAirport()).getAirport());
        flight.setDepartureAirport(lookupTableRepository.getByIATA(flight.getDepartureAirport()).getAirport());
        for(FlightEntity f : flights) {
            String fAirportDeparture = f.getDepartureIata();
            String fAirportArrival = f.getArrivalIata();
            String airportDeparture = lookupTableRepository.getByAirport(flight.getDepartureAirport()).getIATA();
            String airportArrival = lookupTableRepository.getByAirport(flight.getArrivalAirport()).getIATA();
            if(Objects.equals(fAirportDeparture, airportArrival)) {
                String newArrivalAirport = lookupTableRepository.getByIATA(f.getArrivalIata()).getAirport();
                String stop = lookupTableRepository.getByIATA(f.getDepartureIata()).getAirport();
                flight.setArrivalAirport(newArrivalAirport);
                flight.getStops().add(stop);
            } else if(Objects.equals(fAirportArrival, airportDeparture)) {
                String newDepartureAirport = lookupTableRepository.getByIATA(f.getDepartureIata()).getAirport();
                String stop = lookupTableRepository.getByIATA(f.getArrivalIata()).getAirport();
                flight.setDepartureAirport(newDepartureAirport);
                flight.getStops().add(stop);
            }
        }
        return flight;
    }

    @Override
    public Object addFlight(FlightResource flightResource) {
        System.out.println(FLIGHT_MAPPER.fromFlightResource(flightResource));
        return FLIGHT_MAPPER.toFlightResource(flightRepository.save(FLIGHT_MAPPER.fromFlightResource(flightResource)));
    }

    @Override
    public List<FlightResource> getFlights(String origin, String destination, Timestamp departureDate, Timestamp arrivalDate) {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        long yesterdayInMillis = currentDate.getTime() - (24 * 60 * 60 * 1000);
        Timestamp yesterday = new Timestamp(yesterdayInMillis);
        String originIATA = lookupTableService.getIATA(origin);
        String destinationIATA = lookupTableService.getIATA(destination);
        if(departureDate.after(yesterday)) {
            System.out.println(1);
            List<FlightEntity> tempFlights = flightRepository.findByArrivalIataAndDepartureIataAndDepartureDateAfter(originIATA, destinationIATA, departureDate);
            return getDesiredFlights(tempFlights, originIATA, destinationIATA);
        } else if(arrivalDate.after(yesterday)) {
            System.out.println(2);
            List<FlightEntity> tempFlights = flightRepository.findByArrivalIataAndDepartureIataAndArrivalDateAfter(originIATA, destinationIATA, arrivalDate);
            return getDesiredFlights(tempFlights, originIATA, destinationIATA);
        }
        System.out.println(3);
        return null;
    }

    @Override
    public List<FlightResource> getCheapestDirectFlights(String origin, String destination, Timestamp departureDate, Timestamp arrivalDate) {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        long yesterdayInMillis = currentDate.getTime() - (24 * 60 * 60 * 1000);
        Timestamp yesterday = new Timestamp(yesterdayInMillis);
        String originIATA = lookupTableService.getIATA(origin);
        String destinationIATA = lookupTableService.getIATA(destination);
        if(departureDate.after(yesterday)) {
            System.out.println(1);
            List<FlightEntity> tempFlights = flightRepository.findByArrivalIataAndDepartureIataAndDepartureDateAfter(originIATA, destinationIATA, departureDate);
            return getDesiredCheapestDirectFlights(tempFlights, origin, destination);
        } else if(arrivalDate.after(yesterday)) {
            System.out.println(2);
            List<FlightEntity> tempFlights = flightRepository.findByArrivalIataAndDepartureIataAndArrivalDateAfter(originIATA, destinationIATA, arrivalDate);
            return getDesiredCheapestDirectFlights(tempFlights, origin, destination);
        }
        System.out.println(3);
        return null;
    }

    @Override
    public List<FlightResource> getCheapestFlights(String origin, String destination, Timestamp departureDate, Timestamp arrivalDate) {
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        long yesterdayInMillis = currentDate.getTime() - (24 * 60 * 60 * 1000);
        Timestamp yesterday = new Timestamp(yesterdayInMillis);
        String originIATA = lookupTableService.getIATA(origin);
        String destinationIATA = lookupTableService.getIATA(destination);
        if(departureDate.after(yesterday)) {
            System.out.println(1);
            return getDesiredCheapestFlights(originIATA, destinationIATA, departureDate);
        } else if(arrivalDate.after(yesterday)) {
            System.out.println(2);
            return getDesiredCheapestFlights(originIATA, destinationIATA, arrivalDate);
        }
        System.out.println(3);
        return null;
    }

    private FlightResource getDesiredFLight(List<FlightEntity> tempFlights, String origin, String destination){
        List<FlightResource> flights = FLIGHT_MAPPER.toFlightResources(tempFlights);
        FlightResource result = flights.stream().findFirst().orElse(null);
        if(result != null) {
            result.setDepartureAirport(origin);
            result.setArrivalAirport(destination);
        }
        return result;
    }

    private FlightResource getDesiredCheapestDirectFlight(List<FlightEntity> tempFlights, String origin, String destination){
        List<FlightResource> flights = FLIGHT_MAPPER.toFlightResources(tempFlights);
        FlightResource result = flights.stream().min(Comparator.comparing(FlightResource::getPrice)).orElse(null);
        if(result != null) {
            result.setDepartureAirport(origin);
            result.setArrivalAirport(destination);
        }
        return result;
    }

    private List<FlightResource> getDesiredFlights(List<FlightEntity> tempFlights, String origin, String destination){
        List<FlightResource> flights = FLIGHT_MAPPER.toFlightResources(tempFlights).stream().limit(5).collect(Collectors.toList());
        flights.forEach(flightResource -> {
            flightResource.setDepartureAirport(origin);
            flightResource.setArrivalAirport(destination);
        });
        return flights;
    }

    private List<FlightResource> getDesiredCheapestDirectFlights(List<FlightEntity> tempFlights, String origin, String destination){
        List<FlightResource> flights = FLIGHT_MAPPER.toFlightResources(tempFlights).stream().sorted(Comparator.comparing(FlightResource::getPrice)).limit(5).collect(Collectors.toList());
        flights.forEach(flightResource -> {
            flightResource.setDepartureAirport(origin);
            flightResource.setArrivalAirport(destination);
        });
        return flights;
    }

    private List<FlightResource> getDesiredCheapestFlights(String origin, String destination, Timestamp Date){
        List<FlightEntity> departureFlights = flightRepository.findByDepartureIataAndArrivalDateAfter(origin, Date);
        List<FlightEntity> arrivalFlights = flightRepository.findByArrivalIataAndArrivalDateAfter(destination, Date);
        List<FlightResource> resultFlights = new ArrayList<>();
        for(FlightEntity depFlight : departureFlights) {
            arrivalFlights.stream().filter(arrivalFlight -> arrivalFlight.getFlightNumber().equals(depFlight.getFlightNumber())).forEach(arrivalFlight -> {
                resultFlights.add(buildFlight(depFlight.getFlightNumber()));
            });
        }
        List<FlightResource> flights = resultFlights.stream().sorted(Comparator.comparing(FlightResource::getPrice)).limit(5).toList();
        return flights;
    }
}
