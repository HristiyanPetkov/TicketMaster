package com.example.ticketmasterapi.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LookupTableEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String airport;

    @Column(unique = true)
    private String IATA;
}
