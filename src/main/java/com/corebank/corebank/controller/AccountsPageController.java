package com.corebank.corebank.controller;

import com.corebank.corebank.model.CreditCard;
import com.corebank.corebank.service.AccountService;
import com.corebank.corebank.service.CreditCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
@RequestMapping("/accounts")
public class AccountsPageController {

    private final AccountService accountService;
    private final CreditCardService creditCardService;

    public AccountsPageController(AccountService accountService,
                                  CreditCardService creditCardService) {
        this.accountService = accountService;
        this.creditCardService = creditCardService;
    }

    // =====================================================
    @GetMapping({"", "/"})
    public String accountsPage(Model model) {
        model.addAttribute("accounts", accountService.getAll());
        return "accounts";
    }

    // =====================================================
    @GetMapping("/view/{id}")
    public String accountDetails(@PathVariable Long id, Model model) {

        return accountService.getById(id)
                .map(acc -> {
                    model.addAttribute("account", acc);

                    CreditCard card = creditCardService.getCardByAccount(acc.getId());
                    model.addAttribute("creditCard", card);

                    return "account-details";
                })
                .orElse("redirect:/accounts?error=notfound");
    }

    // =====================================================
    @GetMapping("/create")
    public String createAccountPage() {
        return "create-account";
    }

    @PostMapping("/create")
    public String createAccountSubmit(@RequestParam String number,
                                      @RequestParam String type,
                                      RedirectAttributes ra) {
        try {
            accountService.createAccount(number, type);
            ra.addFlashAttribute("toastSuccess", "Account created successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("toastError", e.getMessage());
        }

        return "redirect:/accounts";
    }

    // =====================================================
    @GetMapping("/edit/{id}")
    public String editAccountPage(@PathVariable Long id, Model model) {

        return accountService.getById(id)
                .map(acc -> {
                    model.addAttribute("account", acc);
                    return "edit-account";
                })
                .orElse("redirect:/accounts?error=notfound");
    }

    @PostMapping("/update/{id}")
    public String updateAccount(@PathVariable Long id,
                                @RequestParam String name,
                                @RequestParam String type,
                                RedirectAttributes ra) {

        accountService.updateBasicDetails(id, name, type);
        ra.addFlashAttribute("toastSuccess", "Account updated successfully!");

        return "redirect:/accounts";
    }

    // =====================================================
    @PostMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id, RedirectAttributes ra) {

        boolean deleted = accountService.safeDelete(id);

        if (!deleted) {
            ra.addFlashAttribute("toastError", "Cannot delete account with transactions.");
        } else {
            ra.addFlashAttribute("toastSuccess", "Account deleted successfully!");
        }

        return "redirect:/accounts";
    }

    // =====================================================
    @GetMapping("/{id}/deposit")
    public String depositPage(@PathVariable Long id, Model model) {

        return accountService.getById(id)
                .map(acc -> {
                    model.addAttribute("account", acc);
                    return "deposit";
                })
                .orElse("redirect:/accounts?error=notfound");
    }

    @PostMapping("/{id}/deposit")
    public String depositSubmit(@PathVariable Long id,
                                @RequestParam BigDecimal amount,
                                RedirectAttributes ra) {

        try {
            accountService.deposit(id, amount);
            ra.addFlashAttribute("toastSuccess", "Deposit successful!");
        } catch (Exception e) {
            ra.addFlashAttribute("toastError", e.getMessage());
        }

        return "redirect:/accounts/view/" + id;
    }

    // =====================================================
    @GetMapping("/{id}/withdraw")
    public String withdrawPage(@PathVariable Long id, Model model) {
        model.addAttribute("accountId", id);
        return "withdraw";
    }

    @GetMapping("/{id}/transfer")
    public String transferPage(@PathVariable Long id, Model model) {
        model.addAttribute("accountId", id);
        model.addAttribute("accounts", accountService.getAll());
        return "transfer";
    }

}
