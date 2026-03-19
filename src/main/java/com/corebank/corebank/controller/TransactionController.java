package com.corebank.corebank.controller;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.Transaction;
import com.corebank.corebank.model.enums.TransactionType;
import com.corebank.corebank.service.TransactionService;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("")
    public Transaction create(@RequestBody Transaction t) {
        return service.processTransaction(t);
    }

    @PostMapping("/{id}/withdraw")
    public Transaction withdraw(@PathVariable Long id,
                                @RequestParam BigDecimal amount) {

        Transaction t = new Transaction();
        Account acc = new Account();
        acc.setId(id);

        t.setAccount(acc);
        t.setAmount(amount);
        t.setType(TransactionType.WITHDRAW);

        return service.processTransaction(t);
    }

    @PostMapping("/{fromId}/transfer")
    public Transaction transfer(@PathVariable Long fromId,
                                @RequestParam Long toId,
                                @RequestParam BigDecimal amount) {

        Account fromAcc = new Account(); fromAcc.setId(fromId);
        Account toAcc = new Account();   toAcc.setId(toId);

        Transaction t = new Transaction();
        t.setAccount(fromAcc);
        t.setRelatedAccount(toAcc);
        t.setAmount(amount);
        t.setType(TransactionType.TRANSFER);

        return service.processTransaction(t);
    }

    @GetMapping("/account/{id}")
    public List<Transaction> getByAccount(@PathVariable Long id) {
        return service.getByAccount(id);
    }

    @GetMapping("")
    public List<Transaction> getAll() {
        return service.getAll();
    }
}
