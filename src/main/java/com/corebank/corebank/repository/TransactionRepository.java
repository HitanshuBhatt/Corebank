package com.corebank.corebank.repository;

import com.corebank.corebank.model.Transaction;
import com.corebank.corebank.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // 🔹 All transactions where this account is the MAIN account
    List<Transaction> findByAccountId(Long accountId);

    // 🔹 All transactions where this account RECEIVED money
    List<Transaction> findByRelatedAccountId(Long accountId);

    // 🔹 All transactions for multiple accounts
    List<Transaction> findByAccountIdIn(List<Long> accountIds);

    // 🔹 All CREDIT or DEBIT transactions for an account
    List<Transaction> findByAccountIdAndType(Long accountId, TransactionType type);

    // 🔹 Get most recent transactions for account
    List<Transaction> findTop10ByAccountIdOrderByTimestampDesc(Long accountId);


    //  MONTHLY SPENDING

    List<Transaction> findByAccountIdInAndTimestampBetween(
            List<Long> accountIds,
            LocalDateTime start,
            LocalDateTime end
    );

    List<Transaction> findByAccountIdInAndTypeAndTimestampBetween(
            List<Long> accountIds,
            TransactionType type,
            LocalDateTime start,
            LocalDateTime end
    );
}
