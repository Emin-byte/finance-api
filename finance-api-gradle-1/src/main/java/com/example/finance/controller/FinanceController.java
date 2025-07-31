package com.example.finance.controller;

import com.example.finance.model.Transaction;
import com.example.finance.model.User;
import com.example.finance.service.FinanceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FinanceController {
    private final FinanceService financeService;

    public FinanceController(FinanceService financeService) {
        this.financeService = financeService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest req) {
        User u = financeService.createUser(req.name(), req.initialBalance());
        return ResponseEntity.ok(u);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @Valid @RequestBody UpdateUserRequest req) {
        User u = financeService.updateUser(id, req.name());
        return ResponseEntity.ok(u);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable UUID id) {
        return financeService.getUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/users")
    public List<User> listUsers() { return financeService.listUsers(); }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody CreateTxRequest req) {
        Transaction tx = financeService.createTransaction(req.userId(), req.amount());
        return ResponseEntity.ok(tx);
    }

    @GetMapping("/transactions")
public java.util.List<com.example.finance.dto.TransactionDto> getTransactions(
        @RequestParam(required = false) java.util.UUID userId,
        @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.OffsetDateTime from,
        @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.OffsetDateTime to) {

    java.util.List<com.example.finance.model.Transaction> txs =
            financeService.getTransactions(java.util.Optional.ofNullable(userId), from, to);

    return txs.stream().map(tx -> {
        var user = tx.getUser();
        com.example.finance.dto.UserSummaryDto userDto = new com.example.finance.dto.UserSummaryDto(user.getId(), user.getName(), user.getBalance());
        return new com.example.finance.dto.TransactionDto(tx.getId(), tx.getAmount(), tx.getCreatedAt(), userDto);
    }).toList();
}

    public record CreateUserRequest(@NotBlank String name, @NotNull BigDecimal initialBalance) {}
    public record UpdateUserRequest(@NotBlank String name) {}
    public record CreateTxRequest(@NotNull UUID userId, @NotNull BigDecimal amount) {}
}
