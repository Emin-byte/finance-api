package com.example.finance.repository;

import com.example.finance.model.Transaction;
import com.example.finance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByCreatedAtBetween(OffsetDateTime from, OffsetDateTime to);
    List<Transaction> findByUserAndCreatedAtBetween(User user, OffsetDateTime from, OffsetDateTime to);
}
