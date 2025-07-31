package com.example.finance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    public Transaction() {
        this.id = UUID.randomUUID();
        this.createdAt = OffsetDateTime.now();
    }

    public Transaction(User user, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.user = user;
        this.amount = amount;
        this.createdAt = OffsetDateTime.now();
    }


}
