package com.corebank.corebank.controller;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.Transaction;
import com.corebank.corebank.model.enums.TransactionType;
import com.corebank.corebank.repository.AccountRepository;
import com.corebank.corebank.service.TransactionService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionPageController {

    private final TransactionService transactionService;
    private final AccountRepository accountRepo;

    public TransactionPageController(TransactionService transactionService,
                                     AccountRepository accountRepo) {
        this.transactionService = transactionService;
        this.accountRepo = accountRepo;
    }

    @GetMapping("")
    public String transactionsPage(Model model) {
        List<Transaction> transactions = transactionService.getAll();
        model.addAttribute("transactions", transactions);
        return "transactions";
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable Long id,
                                           @RequestParam BigDecimal amount) {

        try {
            Account acc = accountRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            Transaction t = new Transaction();
            t.setAccount(acc);
            t.setAmount(amount);
            t.setType(TransactionType.WITHDRAW);
            t.setDescription("Withdrawal");
            t.setCategory("WITHDRAW");

            transactionService.processTransaction(t);

            return ResponseEntity.ok("SUCCESS");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }
}
