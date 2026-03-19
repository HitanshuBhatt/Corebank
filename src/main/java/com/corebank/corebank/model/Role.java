package com.corebank.corebank.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name; // ROLE_USER, ROLE_EMPLOYEE, ROLE_ADMIN
}
