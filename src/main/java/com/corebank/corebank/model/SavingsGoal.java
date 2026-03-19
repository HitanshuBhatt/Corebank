package com.corebank.corebank.model;

import jakarta.persistence.*;
import lombok.Data;
import com.corebank.corebank.model.enums.GoalStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
public class SavingsGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private String name;

    private BigDecimal targetAmount;

    private BigDecimal currentAmount = BigDecimal.ZERO;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private GoalStatus status;
}
