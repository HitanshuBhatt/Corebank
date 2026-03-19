package com.corebank.corebank.controller;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.User;
import com.corebank.corebank.model.enums.LoanStatus;
import com.corebank.corebank.repository.AccountRepository;
import com.corebank.corebank.repository.LoanRepository;
import com.corebank.corebank.repository.UserRepository;
import com.corebank.corebank.service.TransactionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class DashboardController {

    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final LoanRepository loanRepo;
    private final TransactionService transactionService;

    public DashboardController(UserRepository userRepo,
                               AccountRepository accountRepo,
                               LoanRepository loanRepo,
                               TransactionService transactionService) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.loanRepo = loanRepo;
        this.transactionService = transactionService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        if (principal == null)
            return "redirect:/login";

        User user = userRepo.findByUsername(principal.getName()).orElse(null);
        model.addAttribute("user", user);

        // use findByOwner_Id instead of findByUser_Id
        List<Account> accounts = accountRepo.findByUser_Id(user.getId());

        model.addAttribute("totalAccounts", accounts.size());

        model.addAttribute("bankAccounts", accountRepo.findAll().size());
        model.addAttribute("activeLoans", loanRepo.countByStatus(LoanStatus.APPROVED));
        model.addAttribute("allLoans", loanRepo.findAll());

        model.addAttribute("totalBalance", 0);
        model.addAttribute("totalCards", 0);

        // Monthly spending
        List<Long> ids = accounts.stream().map(Account::getId).toList();
        model.addAttribute("monthlySpending",
                transactionService.getMonthlySpending(ids));

        return "dashboard";
    }
}
