package com.corebank.corebank.service;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.Transaction;
import com.corebank.corebank.model.enums.TransactionType;
import com.corebank.corebank.repository.AccountRepository;
import com.corebank.corebank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepo;
    private final AccountRepository accountRepo;

    public TransactionService(TransactionRepository transactionRepo,
                              AccountRepository accountRepo) {
        this.transactionRepo = transactionRepo;
        this.accountRepo = accountRepo;
    }

    // ============================================================
    // PROCESS ANY TRANSACTION (DEPOSIT, WITHDRAW, TRANSFER)
    // ============================================================
    public Transaction processTransaction(Transaction t) {

        Account acc = accountRepo.findById(t.getAccount().getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        BigDecimal amt = t.getAmount();

        switch (t.getType()) {

            case DEPOSIT -> {
                acc.setBalance(acc.getBalance().add(amt));
                accountRepo.save(acc);
                return transactionRepo.save(t);
            }

            case WITHDRAW -> {
                if (acc.getBalance().compareTo(amt) < 0)
                    throw new RuntimeException("Insufficient funds");

                acc.setBalance(acc.getBalance().subtract(amt));
                accountRepo.save(acc);
                return transactionRepo.save(t);
            }

            case TRANSFER -> {
                Account toAcc = accountRepo.findById(t.getRelatedAccount().getId())
                        .orElseThrow(() -> new RuntimeException("Target account not found"));

                // Deduct from sender
                if (acc.getBalance().compareTo(amt) < 0)
                    throw new RuntimeException("Insufficient funds");

                acc.setBalance(acc.getBalance().subtract(amt));
                accountRepo.save(acc);

                // Add to receiver
                toAcc.setBalance(toAcc.getBalance().add(amt));
                accountRepo.save(toAcc);

                return transactionRepo.save(t);
            }
        }

        throw new RuntimeException("Unknown transaction type");
    }

    // ============================================================
    // GET ALL OF ONE ACCOUNT
    // ============================================================
    public List<Transaction> getByAccount(Long id) {
        return transactionRepo.findByAccountId(id);
    }

    // ============================================================
    // GET ALL TRANSACTIONS
    // ============================================================
    public List<Transaction> getAll() {
        return transactionRepo.findAll();
    }

    // ============================================================
    // NEW — MONTHLY SPENDING (LAST 30 DAYS)
    // ============================================================
    public BigDecimal getMonthlySpending(List<Long> accountIds) {

        List<Transaction> tx = transactionRepo.findByAccountIdIn(accountIds);

        return tx.stream()
                .filter(t ->
                        (t.getType() == TransactionType.WITHDRAW ||
                                t.getType() == TransactionType.TRANSFER)
                                && t.getTimestamp().toLocalDate()
                                .isAfter(LocalDate.now().minusDays(30))
                )
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
