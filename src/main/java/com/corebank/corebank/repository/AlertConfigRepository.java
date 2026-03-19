package com.corebank.corebank.repository;

import com.corebank.corebank.model.AlertConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlertConfigRepository extends JpaRepository<AlertConfig, Long> {
    Optional<AlertConfig> findByUserId(Long userId);
}
