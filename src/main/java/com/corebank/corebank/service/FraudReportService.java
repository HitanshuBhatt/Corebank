package com.corebank.corebank.service;

import com.corebank.corebank.model.FraudReport;
import com.corebank.corebank.repository.FraudReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudReportService {

    private final FraudReportRepository repo;

    public FraudReportService(FraudReportRepository repo) {
        this.repo = repo;
    }

    public FraudReport save(FraudReport f) {
        return repo.save(f);
    }

    public List<FraudReport> getByCard(Long cardId) {
        return repo.findByCardId(cardId);
    }

    public List<FraudReport> getAll() {
        return repo.findAll();
    }
}
