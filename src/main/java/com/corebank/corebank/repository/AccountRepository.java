package com.corebank.corebank.repository;

import com.corebank.corebank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // correct: field name is "user"
    List<Account> findByUser_Id(Long userId);

    List<Account> findByUser_Username(String username);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String number);
}
