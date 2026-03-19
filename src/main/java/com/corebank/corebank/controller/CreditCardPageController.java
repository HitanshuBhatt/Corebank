package com.corebank.corebank.controller;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.CreditCard;
import com.corebank.corebank.model.User;
import com.corebank.corebank.service.AccountService;
import com.corebank.corebank.service.CreditCardService;
import com.corebank.corebank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CreditCardPageController {

    private final CreditCardService creditCardService;
    private final UserService userService;
    private final AccountService accountService;

    @GetMapping("/credit-cards")
    public String creditCardDashboard(Model model, Principal principal) {

        // 1) Not logged in → send to login
        if (principal == null) {
            return "redirect:/login";
        }

        // 2) Find logged-in user
        User user = userService
                .findByUsername(principal.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        // 3) Get this user's accounts
        List<Account> accounts = accountService.getByOwner(user.getId());
        Account account = accounts.isEmpty() ? null : accounts.get(0); // just use first account

        // 4) If user has no accounts → no card
        if (account == null) {
            model.addAttribute("creditCard", null);
            return "credit-cards";
        }

        // 5) Get credit card attached to this account
        CreditCard card = creditCardService.getCardByAccount(account.getId());
        model.addAttribute("creditCard", card);

        return "credit-cards";
    }
}
