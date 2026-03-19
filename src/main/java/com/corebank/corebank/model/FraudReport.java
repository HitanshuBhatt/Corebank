package com.corebank.corebank.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class FraudReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CreditCard card;

    private LocalDateTime reportedAt = LocalDateTime.now();

    private String description;

    private boolean resolved = false;
}
