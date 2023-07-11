package com.example.ticketmasterapi.clients.skyscanner;

import com.example.ticketmasterapi.clients.skyscanner.dto.Content;
import com.example.ticketmasterapi.clients.skyscanner.dto.Data;
import com.example.ticketmasterapi.clients.skyscanner.dto.FlightDtoSc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.SocketOption;
import java.sql.SQLOutput;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkyscannerClient {
    private final WebClient webClient;

    @Value("${skyscanner.url}")
    private String url;

    @Value("${skyscanner.key}")
    private String key;

    public Collection<FlightDtoSc> getFlightsPrice(String origin, String destination, String date) {
        String originIata = "\"" + origin + "\"";
        String destinationIata = "\"" + destination + "\"";
        String requestJson = "{ \"query\": { \"currency\": \"EUR\", \"locale\": \"en-US\", \"market\": \"US\"," +
                " \"queryLegs\": [ { \"originPlace\": { \"queryPlace\": { \"iata\":" + originIata +  "} }," +
                " \"destinationPlace\": { \"queryPlace\": { \"iata\":" + destinationIata + "} }," +
                " \"fixedDate\": { \"year\": 2023, \"month\": 7, \"day\": 12 } } ] } }";

        Data data = webClient.post()
                .uri(url)
                .header("x-api-key", key)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestJson))
                .retrieve()
                .bodyToMono(Data.class)
                .block();

        return data.content.results.quotes.values();
    }
}
