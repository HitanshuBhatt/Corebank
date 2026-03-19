package com.corebank.corebank.controller;

import com.corebank.corebank.model.Loan;
import com.corebank.corebank.model.enums.LoanStatus;
import com.corebank.corebank.repository.LoanRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/loan-approval")
public class LoanApprovalController {

    private final LoanRepository loanRepo;

    public LoanApprovalController(LoanRepository loanRepo) {
        this.loanRepo = loanRepo;
    }

    // SHOW ALL PENDING LOANS
    @GetMapping
    public String showPendingLoans(Model model) {
        List<Loan> pendingLoans = loanRepo.findByStatus(LoanStatus.PENDING);
        model.addAttribute("pendingLoans", pendingLoans);
        return "loan-approval"; // thymeleaf page
    }

    // APPROVE LOAN
    @PostMapping("/approve/{id}")
    public String approveLoan(@PathVariable Long id) {
        Loan loan = loanRepo.findById(id).orElse(null);
        if (loan != null) {
            loan.setStatus(LoanStatus.APPROVED);
            loanRepo.save(loan);
        }
        return "redirect:/loan-approval";
    }

    // REJECT LOAN
    @PostMapping("/reject/{id}")
    public String rejectLoan(@PathVariable Long id) {
        Loan loan = loanRepo.findById(id).orElse(null);
        if (loan != null) {
            loan.setStatus(LoanStatus.REJECTED);
            loanRepo.save(loan);
        }
        return "redirect:/loan-approval";
    }
}
