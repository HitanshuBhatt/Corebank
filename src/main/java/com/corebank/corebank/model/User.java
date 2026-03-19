package com.corebank.corebank.model;

import com.corebank.corebank.model.enums.CreditCardStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String fullName;

    private boolean enabled = true;

    // ==============================
    // ROLES
    // ==============================
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // ==============================
    // ACCOUNTS OWNED BY USER
    // ==============================
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts;

    // ==============================
    // CREDIT CARD INFO
    // ==============================
    private Integer creditScore;

    @Column(length = 20)
    private String creditCardNumber;

    @Enumerated(EnumType.STRING)
    private CreditCardStatus creditCardStatus = CreditCardStatus.CANCELLED;
}
