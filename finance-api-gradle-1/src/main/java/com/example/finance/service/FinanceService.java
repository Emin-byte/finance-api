package com.example.finance.service;

import com.example.finance.model.Transaction;
import com.example.finance.model.User;
import com.example.finance.repository.TransactionRepository;
import com.example.finance.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;


    public List<User> listUsers() { return userRepository.findAll(); }
    public Optional<User> getUser(UUID id) { return userRepository.findById(id); }

    public User createUser(String name, BigDecimal initialBalance) {
        User user = new User(name, initialBalance);
        return userRepository.save(user);
    }

    public User updateUser(UUID id, String name) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(name);
        return userRepository.save(user);
    }

    @Transactional
    public Transaction createTransaction(UUID userId, BigDecimal amount) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Transaction tx = new Transaction(user, amount);
        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);
        return transactionRepository.save(tx);
    }

    public List<Transaction> getTransactions(Optional<UUID> userId, OffsetDateTime from, OffsetDateTime to) {
        if (userId.isPresent()) {
            User user = userRepository.findById(userId.get()).orElseThrow(() -> new RuntimeException("User not found"));
            return transactionRepository.findByUserAndCreatedAtBetween(user, from, to);
        }
        return transactionRepository.findByCreatedAtBetween(from, to);
    }
}
