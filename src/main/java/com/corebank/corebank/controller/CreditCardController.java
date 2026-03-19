package com.corebank.corebank.controller;

import com.corebank.corebank.model.CreditCard;
import com.corebank.corebank.model.enums.CreditCardStatus;
import com.corebank.corebank.service.CreditCardService;
import com.corebank.corebank.service.AccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/credit-cards")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final AccountService accountService;

    // ✅ FIXED — YOU WERE MISSING THIS PAGE
    @GetMapping("/apply")
    public String applyPage() {
        return "credit-card-apply";
    }

    @PostMapping("/apply")
    public String applySubmit(@RequestParam Long accountId,
                              @RequestParam int creditScore,
                              RedirectAttributes ra,
                              Model model) {

        try {
            CreditCard card = creditCardService.applyForAccount(accountId, creditScore);
            model.addAttribute("card", card);

            if (card.getStatus() == CreditCardStatus.APPROVED) {
                ra.addFlashAttribute("toastSuccess", "🎉 Credit card approved!");
            } else {
                ra.addFlashAttribute("toastError", "❌ Credit card rejected!");
            }

        } catch (Exception e) {
            ra.addFlashAttribute("toastError", e.getMessage());
        }

        return "redirect:/credit-cards";
    }

    @GetMapping("/validate-account")
    @ResponseBody
    public String validateAccount(@RequestParam String accountNumber) {
        var acc = accountService.getByAccountNumber(accountNumber);
        return (acc == null) ? "INVALID" : acc.getId().toString();
    }

    @GetMapping("/details/{accountId}")
    public String cardDetails(@PathVariable Long accountId, Model model) {
        CreditCard card = creditCardService.getCardByAccount(accountId);
        model.addAttribute("card", card);
        return "creditcard-details";
    }

}
