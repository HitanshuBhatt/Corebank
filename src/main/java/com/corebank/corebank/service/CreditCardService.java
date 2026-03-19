package com.corebank.corebank.service;

import com.corebank.corebank.model.Account;
import com.corebank.corebank.model.CreditCard;
import com.corebank.corebank.model.enums.CreditCardStatus;
import com.corebank.corebank.repository.AccountRepository;
import com.corebank.corebank.repository.CreditCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CreditCardService {

    private final CreditCardRepository creditCardRepo;
    private final AccountRepository accountRepo;

    // Generate random 16-digit number
    private String generateCardNumber() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    // APPLY for credit card
    public CreditCard applyForAccount(Long accountId, int creditScore) {

        Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Prevent duplicates
        if (creditCardRepo.findByAccount_Id(accountId).isPresent()) {
            throw new RuntimeException("This account already has a credit card");
        }

        CreditCard card = new CreditCard();
        card.setAccount(account);
        card.setCreditScore(creditScore);

        // ⭐ Your requested logic:
        // <500 → REJECT, ≥500 → APPROVE with 10000 CAD
        if (creditScore < 500) {
            card.setStatus(CreditCardStatus.REJECTED);
            card.setCreditLimit(0);
            card.setCardNumber(null);  // rejected applications don't get a card number
        } else {
            card.setStatus(CreditCardStatus.APPROVED);
            card.setCreditLimit(10000);
            card.setCardNumber(generateCardNumber());
        }

        return creditCardRepo.save(card);
    }

    // Get card by account
    public CreditCard getCardByAccount(Long accountId) {
        return creditCardRepo.findByAccount_Id(accountId).orElse(null);
    }

    // Update card status if needed
    public void updateStatus(Long cardId, CreditCardStatus status) {
        CreditCard card = creditCardRepo.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        card.setStatus(status);
        creditCardRepo.save(card);
    }
}
