package com.corebank.corebank.model;

import com.corebank.corebank.model.enums.CreditCardStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 16 digit unique card number
    @Column(unique = true, length = 16, nullable = false)
    private String cardNumber;

    private int creditScore;        // User input
    private int creditLimit;        // Auto assigned

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CreditCardStatus status;

    // ⭐ CREDIT CARD BELONGS TO ONE ACCOUNT ONLY
    @OneToOne
    @JoinColumn(name = "account_id", unique = true, nullable = false)
    private Account account;
}
