package com.corebank.corebank.service;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.Transaction;
import com.corebank.corebank.model.User;
import com.corebank.corebank.model.enums.AccountStatus;
import com.corebank.corebank.model.enums.TransactionType;
import com.corebank.corebank.repository.AccountRepository;
import com.corebank.corebank.repository.TransactionRepository;
import com.corebank.corebank.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository repo;
    private final TransactionRepository transactionRepo;
    private final UserRepository userRepo;

    public AccountService(AccountRepository repo,
                          TransactionRepository transactionRepo,
                          UserRepository userRepo) {
        this.repo = repo;
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
    }

    // ----------------------------------------------------
    // BASIC CRUD
    // ----------------------------------------------------
    public Account save(Account account) {
        return repo.save(account);
    }

    public Optional<Account> getById(Long id) {
        return repo.findById(id);
    }

    public List<Account> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    // ----------------------------------------------------
    // FIND ACCOUNTS BY OWNER  (FINAL CORRECT VERSION)
    // ----------------------------------------------------
    public List<Account> getByOwner(Long ownerId) {
        return repo.findByUser_Id(ownerId);
    }

    // ----------------------------------------------------
// FIND ACCOUNTS BY USER (CORRECT VERSION)
// ----------------------------------------------------
    public List<Account> getByUser(Long userId) {
        return repo.findByUser_Id(userId);
    }


    // ----------------------------------------------------
    // FIND ACCOUNT BY ACCOUNT NUMBER
    // ----------------------------------------------------
    public Account getByAccountNumber(String accountNumber) {
        return repo.findByAccountNumber(accountNumber).orElse(null);
    }

    public Account getAccountByNumber(String accountNumber) {
        return repo.findByAccountNumber(accountNumber).orElse(null);
    }

    public Account getAccountByNumber(Long accountNumber) {
        return repo.findByAccountNumber(String.valueOf(accountNumber)).orElse(null);
    }

    // ----------------------------------------------------
    // CREATE ACCOUNT
    // ----------------------------------------------------
    public Account createAccount(String number, String type) {

        if (repo.existsByAccountNumber(number)) {
            throw new RuntimeException("Account number already exists!");
        }

        Account acc = new Account();
        acc.setAccountNumber(number);
        acc.setType(Enum.valueOf(com.corebank.corebank.model.enums.AccountType.class, type.toUpperCase()));
        acc.setBalance(BigDecimal.ZERO);
        acc.setAccountName("New Account");

        return repo.save(acc);
    }

    // ----------------------------------------------------
    // UPDATE ACCOUNT
    // ----------------------------------------------------
    public boolean updateBasicDetails(Long id, String name, String type) {

        Account acc = repo.findById(id).orElse(null);
        if (acc == null) return false;

        acc.setAccountName(name);
        acc.setType(Enum.valueOf(com.corebank.corebank.model.enums.AccountType.class, type.toUpperCase()));
        repo.save(acc);

        return true;
    }

    // ----------------------------------------------------
    // SAFE DELETE ACCOUNT
    // ----------------------------------------------------
    public boolean safeDelete(Long id) {

        Account acc = repo.findById(id).orElse(null);
        if (acc == null) return false;

        if (acc.getTransactions() != null && !acc.getTransactions().isEmpty()) {
            acc.setStatus(AccountStatus.CLOSED);
            repo.save(acc);
            return true;
        }

        repo.delete(acc);
        return true;
    }

    // ----------------------------------------------------
    // DEPOSIT
    // ----------------------------------------------------
    public void deposit(Long id, BigDecimal amount) {

        Account acc = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Deposit amount must be greater than zero");
        }

        // Update balance
        acc.setBalance(acc.getBalance().add(amount));
        repo.save(acc);

        // Log transaction
        Transaction t = new Transaction();
        t.setAccount(acc);
        t.setAmount(amount);
        t.setDescription("Deposit");
        t.setCategory("DEPOSIT");
        t.setType(TransactionType.DEPOSIT);

        transactionRepo.save(t);
    }

    // ----------------------------------------------------
    // GET USER ID FROM USERNAME (SAFE)
    // ----------------------------------------------------
    public Long getUserIdFromUsername(String username) {

        User user = userRepo.findByUsername(username).orElse(null);

        if (user == null) return null;

        return user.getId();
    }
    // ----------------------------------------------------
// GET USER ID FROM ACCOUNT ID
// ----------------------------------------------------
    public Long getUserIdFromAccount(Long accountId) {
        return repo.findById(accountId)
                .map(acc -> acc.getUser() != null ? acc.getUser().getId() : null)
                .orElse(null);
    }

}
