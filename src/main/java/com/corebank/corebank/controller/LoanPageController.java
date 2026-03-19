package com.corebank.corebank.controller;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.Loan;
import com.corebank.corebank.model.enums.LoanStatus;
import com.corebank.corebank.model.enums.LoanType;
import com.corebank.corebank.repository.AccountRepository;
import com.corebank.corebank.repository.LoanRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/loan")
public class LoanPageController {

    private final AccountRepository accountRepo;
    private final LoanRepository loanRepo;

    public LoanPageController(AccountRepository accountRepo, LoanRepository loanRepo) {
        this.accountRepo = accountRepo;
        this.loanRepo = loanRepo;
    }

    @GetMapping("/apply")
    public String showApplyLoanPage(Model model) {
        return "loan-apply";
    }

    @PostMapping("/apply")
    public String applyLoan(@RequestParam Long accountId,
                            @RequestParam BigDecimal amount,
                            @RequestParam Integer termMonths) {

        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Loan loan = new Loan();
        loan.setAccount(account);
        loan.setAmount(amount);
        loan.setRemaining(amount);
        loan.setTermMonths(termMonths);
        loan.setType(LoanType.PERSONAL);
        loan.setStatus(LoanStatus.PENDING);
        loan.setInterestRate(new BigDecimal("5.0"));
        loan.setCreatedAt(LocalDateTime.now());

        loanRepo.save(loan);

        return "redirect:/dashboard";
    }
}
