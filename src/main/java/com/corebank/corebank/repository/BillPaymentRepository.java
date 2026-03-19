package com.corebank.corebank.repository;

import com.corebank.corebank.model.BillPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillPaymentRepository extends JpaRepository<BillPayment, Long> {

    // All payments for a given customer (account owner)
    List<BillPayment> findByUserId(Long userId);

    // All payments from a given account
    List<BillPayment> findByFromAccountId(Long accountId);
}
