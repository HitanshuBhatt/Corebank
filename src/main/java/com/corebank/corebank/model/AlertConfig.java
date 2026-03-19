package com.corebank.corebank.model;
import com.corebank.corebank.model.enums.NotificationChannel;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AlertConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private double lowBalanceThreshold;
    private double largeTxThreshold;

    private int billReminderDaysBefore = 3;

    @Enumerated(EnumType.STRING)
    private NotificationChannel notificationsChannel;

    private boolean enabled = true;
}
