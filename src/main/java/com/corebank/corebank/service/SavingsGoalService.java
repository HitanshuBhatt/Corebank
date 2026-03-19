package com.corebank.corebank.service;

import com.corebank.corebank.model.SavingsGoal;
import com.corebank.corebank.repository.SavingsGoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavingsGoalService {

    private final SavingsGoalRepository repo;

    public SavingsGoalService(SavingsGoalRepository repo) {
        this.repo = repo;
    }

    public SavingsGoal save(SavingsGoal g) {
        return repo.save(g);
    }

    public List<SavingsGoal> getByUser(Long userId) {
        return repo.findByUserId(userId);
    }

    public List<SavingsGoal> getAll() {
        return repo.findAll();
    }
}

