package com.corebank.corebank.controller;

import com.corebank.corebank.model.Budget;
import com.corebank.corebank.service.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService service;

    public BudgetController(BudgetService service) {
        this.service = service;
    }

    @GetMapping("/user/{userId}")
    public List<Budget> getByUser(@PathVariable Long userId) {
        return service.getByUser(userId);
    }

    @PostMapping
    public Budget create(@RequestBody Budget b) {
        return service.save(b);
    }
}
