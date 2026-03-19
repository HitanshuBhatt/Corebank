package com.corebank.corebank.model;

import com.corebank.corebank.model.enums.AccountType;
import com.corebank.corebank.model.enums.AccountStatus;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    private String accountName;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    private BigDecimal balance = BigDecimal.ZERO;

    // 🔥 FIXED — matches your DB (user_id)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
}
