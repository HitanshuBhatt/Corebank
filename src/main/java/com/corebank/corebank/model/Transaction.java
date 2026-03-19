package com.corebank.corebank.model;

import com.corebank.corebank.model.enums.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private BigDecimal amount;

    private String description;

    private String category;

    @ManyToOne
    @JoinColumn(name = "related_account_id")
    private Account relatedAccount;

    private LocalDateTime timestamp = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    // ===========================
    // GETTERS & SETTERS
    // ===========================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Account getAccount() { return account; }
    public void setAccount(Account account) { this.account = account; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Account getRelatedAccount() { return relatedAccount; }
    public void setRelatedAccount(Account relatedAccount) { this.relatedAccount = relatedAccount; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }



}
