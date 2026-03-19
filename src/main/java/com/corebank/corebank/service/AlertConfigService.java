package com.corebank.corebank.service;

import com.corebank.corebank.model.AlertConfig;
import com.corebank.corebank.repository.AlertConfigRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlertConfigService {

    private final AlertConfigRepository repo;

    public AlertConfigService(AlertConfigRepository repo) {
        this.repo = repo;
    }

    public AlertConfig save(AlertConfig config) {
        return repo.save(config);
    }

    public Optional<AlertConfig> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }
}
