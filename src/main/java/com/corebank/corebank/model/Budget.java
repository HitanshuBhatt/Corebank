package com.corebank.corebank.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String category;

    private double monthlyLimit;

    private String monthYear; // 2025-11
}
