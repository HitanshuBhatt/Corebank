package com.corebank.corebank.controller;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.Loan;
import com.corebank.corebank.model.Transaction;
import com.corebank.corebank.model.enums.LoanStatus;
import com.corebank.corebank.model.enums.TransactionType;
import com.corebank.corebank.repository.AccountRepository;
import com.corebank.corebank.repository.LoanRepository;
import com.corebank.corebank.repository.TransactionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/loan")
public class LoanActionController {

    private final LoanRepository loanRepo;
    private final AccountRepository accountRepo;
    private final TransactionRepository txRepo;

    public LoanActionController(LoanRepository loanRepo,
                                AccountRepository accountRepo,
                                TransactionRepository txRepo) {
        this.loanRepo = loanRepo;
        this.accountRepo = accountRepo;
        this.txRepo = txRepo;
    }

    @PostMapping("/approve/{id}")
    public String approveLoan(@PathVariable Long id) {

        Loan loan = loanRepo.findById(id).orElse(null);
        if (loan == null) return "redirect:/dashboard";

        // update loan
        loan.setStatus(LoanStatus.APPROVED);
        loanRepo.save(loan);

        // deposit money into account
        Account acc = loan.getAccount();
        acc.setBalance(acc.getBalance().add(loan.getAmount()));
        accountRepo.save(acc);

        // transaction record
        Transaction tx = new Transaction();
        tx.setAccount(acc);
        tx.setAmount(loan.getAmount());
        tx.setType(TransactionType.LOAN_CREDIT);
        tx.setDescription("Loan Approved");
        tx.setTimestamp(LocalDateTime.now());
        txRepo.save(tx);

        return "redirect:/dashboard";
    }

    @PostMapping("/reject/{id}")
    public String rejectLoan(@PathVariable Long id) {

        loanRepo.findById(id).ifPresent(loan -> {
            loan.setStatus(LoanStatus.REJECTED);
            loanRepo.save(loan);
        });

        return "redirect:/dashboard";
    }
}
