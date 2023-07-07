package com.example.ticketmasterapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TicketMasterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketMasterApiApplication.class, args);
    }

}
