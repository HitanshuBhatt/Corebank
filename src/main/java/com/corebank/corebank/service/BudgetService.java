package com.corebank.corebank.service;

import com.corebank.corebank.model.Budget;
import com.corebank.corebank.model.Transaction;
import com.corebank.corebank.model.enums.TransactionType;
import com.corebank.corebank.repository.BudgetRepository;
import com.corebank.corebank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BudgetService {

    private final TransactionRepository transactionRepo;
    private final BudgetRepository budgetRepo;

    public BudgetService(TransactionRepository transactionRepo,
                         BudgetRepository budgetRepo) {
        this.transactionRepo = transactionRepo;
        this.budgetRepo = budgetRepo;
    }

    // ==========================================================
    //  A) API FOR YOUR CONTROLLER  (REQUIRED)
    // ==========================================================

    // 🔹 Get all budgets for a user
    public List<Budget> getByUser(Long userId) {
        return budgetRepo.findByUser_Id(userId);
    }

    // 🔹 Create/update budget
    public Budget save(Budget budget) {
        return budgetRepo.save(budget);
    }

    // ==========================================================
    //  B) BUDGET ANALYTICS (Your existing functions)
    // ==========================================================

    // 1. MONTHLY SPENDING (Past 30 Days)
    public BigDecimal getMonthlySpending(List<Long> accountIds) {

        List<Transaction> tx = transactionRepo.findByAccountIdIn(accountIds);

        return tx.stream()
                .filter(t -> t.getType() == TransactionType.WITHDRAW
                        || t.getType() == TransactionType.TRANSFER)
                .filter(t -> t.getTimestamp().toLocalDate().isAfter(LocalDate.now().minusDays(30)))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 2. MONTHLY INCOME (Deposits Only)
    public BigDecimal getMonthlyIncome(List<Long> accountIds) {

        List<Transaction> tx = transactionRepo.findByAccountIdIn(accountIds);

        return tx.stream()
                .filter(t -> t.getType() == TransactionType.DEPOSIT)
                .filter(t -> t.getTimestamp().toLocalDate().isAfter(LocalDate.now().minusDays(30)))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // 3. CATEGORY TOTALS (Pie Chart)
    public Map<String, BigDecimal> getCategoryTotals(List<Long> accountIds) {

        List<Transaction> tx = transactionRepo.findByAccountIdIn(accountIds);

        Map<String, BigDecimal> result = new HashMap<>();

        for (Transaction t : tx) {
            if (t.getCategory() == null) continue;

            String category = t.getCategory();

            result.put(category,
                    result.getOrDefault(category, BigDecimal.ZERO)
                            .add(t.getAmount()));
        }

        return result;
    }

    // 4. RECENT EXPENSES LIST (Last 10)
    public List<Transaction> getRecentExpenses(List<Long> accountIds) {

        List<Transaction> tx = transactionRepo.findByAccountIdIn(accountIds);

        return tx.stream()
                .filter(t -> t.getType() == TransactionType.WITHDRAW
                        || t.getType() == TransactionType.TRANSFER)
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    // 5. MONTHLY TOTALS (Line Chart)
    public Map<String, BigDecimal> getMonthlyTotals(List<Long> accountIds) {

        List<Transaction> tx = transactionRepo.findByAccountIdIn(accountIds);

        Map<String, BigDecimal> result = new LinkedHashMap<>();

        // Last 6 months
        for (int i = 5; i >= 0; i--) {
            LocalDate month = LocalDate.now().minusMonths(i);
            String label = month.getMonth().toString().substring(0, 3);

            BigDecimal total = tx.stream()
                    .filter(t -> t.getTimestamp().getMonth() == month.getMonth())
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            result.put(label, total);
        }

        return result;
    }
}
