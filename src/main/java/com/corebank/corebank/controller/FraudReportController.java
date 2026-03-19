package com.corebank.corebank.controller;

import com.corebank.corebank.model.FraudReport;
import com.corebank.corebank.service.FraudReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fraud")
public class FraudReportController {

    private final FraudReportService service;

    public FraudReportController(FraudReportService service) {
        this.service = service;
    }

    @GetMapping
    public List<FraudReport> getAll() {
        return service.getAll();
    }

    @GetMapping("/card/{cardId}")
    public List<FraudReport> getByCard(@PathVariable Long cardId) {
        return service.getByCard(cardId);
    }

    @PostMapping
    public FraudReport create(@RequestBody FraudReport f) {
        return service.save(f);
    }
}
