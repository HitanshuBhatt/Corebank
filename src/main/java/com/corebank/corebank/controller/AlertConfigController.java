package com.corebank.corebank.controller;

import com.corebank.corebank.model.AlertConfig;
import com.corebank.corebank.service.AlertConfigService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class AlertConfigController {

    private final AlertConfigService service;

    public AlertConfigController(AlertConfigService service) {
        this.service = service;
    }

    @GetMapping("/user/{userId}")
    public AlertConfig getByUser(@PathVariable Long userId) {
        return service.getByUser(userId).orElse(null);
    }

    @PostMapping
    public AlertConfig createOrUpdate(@RequestBody AlertConfig c) {
        return service.save(c);
    }
}
