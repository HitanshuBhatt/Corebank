package com.corebank.corebank.controller;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.Transaction;
import com.corebank.corebank.model.enums.TransactionType;
import com.corebank.corebank.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    private final TransactionService service;

    public TransactionRestController(TransactionService service) {
        this.service = service;
    }

    // ----------------------------
    // DEPOSIT (Used by deposit.html)
    // ----------------------------
    @PostMapping("/deposit/{id}")
    public Transaction deposit(@PathVariable Long id,
                               @RequestParam BigDecimal amount) {

        Transaction t = new Transaction();
        Account acc = new Account();
        acc.setId(id);

        t.setAccount(acc);
        t.setAmount(amount);
        t.setType(TransactionType.DEPOSIT);

        return service.processTransaction(t);
    }
}
