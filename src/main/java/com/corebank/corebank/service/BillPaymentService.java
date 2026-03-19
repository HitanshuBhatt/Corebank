package com.corebank.corebank.service;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.BillPayment;
import com.corebank.corebank.model.Transaction;
import com.corebank.corebank.model.enums.TransactionType;
import com.corebank.corebank.repository.AccountRepository;
import com.corebank.corebank.repository.BillPaymentRepository;
import com.corebank.corebank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillPaymentService {

    private final BillPaymentRepository billPaymentRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BillPayment submitPayment(Long customerId, Long accountId, String title, BigDecimal amount) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found."));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance.");
        }

        // Deduct amount
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        // Log transaction
        Transaction t = new Transaction();
        t.setAccount(account);
        t.setAmount(amount);
        t.setType(TransactionType.BILL_PAYMENT);
        t.setDescription(title);
        t.setCategory("Bill Payment");
        t.setTimestamp(LocalDateTime.now());

        transactionRepository.save(t);

        // Save bill payment
        BillPayment p = new BillPayment();
        p.setFromAccount(account);
        p.setUserId(customerId); // THIS WAS THE MAIN FIX
        p.setTitle(title);
        p.setAmount(amount);
        p.setTimestamp(LocalDateTime.now());

        return billPaymentRepository.save(p);
    }

    public List<BillPayment> getAll() {
        return billPaymentRepository.findAll();
    }

    public List<BillPayment> getByAccount(Long accountId) {
        return billPaymentRepository.findByFromAccountId(accountId);
    }

    public List<BillPayment> getByUser(Long userId) {
        return billPaymentRepository.findByUserId(userId);
    }
}
