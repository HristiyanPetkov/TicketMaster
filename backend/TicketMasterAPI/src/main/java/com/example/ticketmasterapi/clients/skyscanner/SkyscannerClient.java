package com.example.ticketmasterapi.clients.skyscanner;

import com.example.ticketmasterapi.clients.skyscanner.dto.Data;
import com.example.ticketmasterapi.clients.skyscanner.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkyscannerClient {
    private final WebClient webClient;

    @Value("${skyscanner.url}")
    private String url;

    @Value("${skyscanner.key}")
    private String key;

    public Result getFlightsPrice(String origin, String destination, String date) {
        String originIata = "\"" + origin + "\"";
        String destinationIata = "\"" + destination + "\"";

        date = date.split(" ")[0];
        List<String> dateComponents = List.of(date.split("-"));

        String requestJson = "{ \"query\": { \"currency\": \"EUR\", \"locale\": \"en-US\", \"market\": \"US\"," +
                " \"queryLegs\": [ { \"originPlace\": { \"queryPlace\": { \"iata\":" + originIata +  "} }," +
                " \"destinationPlace\": { \"queryPlace\": { \"iata\":" + destinationIata + "} }," +
                " \"fixedDate\": { \"year\":" + dateComponents.get(0) +
                ", \"month\":" + Integer.parseInt(dateComponents.get(1)) +
                ", \"day\":" + Integer.parseInt(dateComponents.get(2)) + "} } ] } }";

        Data data = webClient.post()
                .uri(url)
                .header("x-api-key", key)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestJson))
                .retrieve()
                .bodyToMono(Data.class)
                .block();

        assert data != null;
        return data.content.results;
    }
}
