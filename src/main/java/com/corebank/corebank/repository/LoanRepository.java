package com.corebank.corebank.repository;

import com.corebank.corebank.model.Loan;
import com.corebank.corebank.model.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByAccountIdInAndStatus(List<Long> accountIds, LoanStatus status);

    List<Loan> findByAccount_IdIn(List<Long> accountIds);

    long countByStatus(LoanStatus status);
}
