package com.corebank.corebank.controller;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping
    public List<Account> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable Long id) {
        return service.getById(id).orElse(null);
    }

    @GetMapping("/user/{userId}")
    public List<Account> getByUser(@PathVariable Long userId) {
        return service.getByOwner(userId);
    }

    @PostMapping
    public Account create(@RequestBody Account acc) {
        return service.save(acc);
    }

    @PutMapping("/{id}")
    public Account update(@PathVariable Long id, @RequestBody Account acc) {
        acc.setId(id);
        return service.save(acc);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
