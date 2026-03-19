package com.corebank.corebank.service;

import com.corebank.corebank.model.User;
import com.corebank.corebank.model.enums.CreditCardStatus;
import com.corebank.corebank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;

    // ============================================
    // BASIC CRUD
    // ============================================

    public List<User> getAll() {
        return repo.findAll();
    }

    public Optional<User> getById(Long id) {
        return repo.findById(id);
    }

    public User save(User user) {
        return repo.save(user);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    // ============================================
    // FIND BY USERNAME / EMAIL
    // ============================================

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    // ============================================
    // CREDIT SCORE UPDATE
    // ============================================

    public boolean updateCreditScore(Long userId, Integer score) {

        User user = repo.findById(userId).orElse(null);
        if (user == null) return false;

        if (score < 300 || score > 900) return false; // basic validation

        user.setCreditScore(score);
        repo.save(user);

        return true;
    }

    // ============================================
    // ASSIGN CREDIT CARD NUMBER (12-digit)
    // ============================================

    public boolean assignCreditCard(Long userId, String cardNumber) {

        User user = repo.findById(userId).orElse(null);
        if (user == null) return false;

        user.setCreditCardNumber(cardNumber);
        user.setCreditCardStatus(CreditCardStatus.ACTIVE);

        repo.save(user);
        return true;
    }

    // ============================================
    // CHANGE CREDIT CARD STATUS
    // ============================================

    public boolean updateCreditCardStatus(Long userId, CreditCardStatus status) {

        User user = repo.findById(userId).orElse(null);
        if (user == null) return false;

        user.setCreditCardStatus(status);

        repo.save(user);
        return true;
    }
}
