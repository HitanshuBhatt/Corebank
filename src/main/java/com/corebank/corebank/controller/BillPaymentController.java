package com.corebank.corebank.controller;

import com.corebank.corebank.service.BillPaymentService;
import com.corebank.corebank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/bill-payments")
@RequiredArgsConstructor
public class BillPaymentController {

    private final BillPaymentService service;
    private final AccountService accountService;

    // SHOW ALL BILL PAYMENTS (EMPLOYEE VIEW)
    @GetMapping("")
    public String billPaymentsPage(Model model, Principal principal) {

        if (principal == null)
            return "redirect:/login";

        Long employeeId = accountService.getUserIdFromUsername(principal.getName());

        // show ALL payments employee submitted for customers
        model.addAttribute("payments", service.getAll());

        return "bill-payments";
    }

    // SHOW NEW PAYMENT FORM
    @GetMapping("/new")
    public String newPaymentPage(Model model, Principal principal) {

        if (principal == null)
            return "redirect:/login";

        // Employee must pick ANY customer account
        model.addAttribute("accounts", accountService.getAll());

        return "bill-payment-form";
    }

    // SUBMIT PAYMENT
    @PostMapping("/submit")
    public String submitPayment(
            @RequestParam Long accountId,
            @RequestParam String title,
            @RequestParam BigDecimal amount,
            Principal principal) {

        if (principal == null)
            return "redirect:/login";

        // Determine the customer ID
        Long customerId = accountService.getUserIdFromAccount(accountId);

        // Save payment under CUSTOMER, not employee
        service.submitPayment(customerId, accountId, title, amount);

        return "redirect:/bill-payments?success=true";
    }
}
