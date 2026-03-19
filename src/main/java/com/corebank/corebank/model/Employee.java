package com.corebank.corebank.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String employeeNumber;

    @Column(unique = true)
    private String username;

    private String password;
    private String fullName;

    // CLERK, MANAGER
    private String role;

    private boolean enabled = true;
}
