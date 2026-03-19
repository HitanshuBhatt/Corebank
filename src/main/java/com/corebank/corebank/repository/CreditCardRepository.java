package com.corebank.corebank.repository;

import com.corebank.corebank.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    // Find credit card by account
    Optional<CreditCard> findByAccount_Id(Long accountId);
}
