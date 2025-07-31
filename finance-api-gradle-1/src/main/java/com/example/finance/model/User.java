package com.example.finance.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @NotBlank
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    public User() {
        this.id = UUID.randomUUID();
        this.balance = BigDecimal.ZERO;
    }

    public User(String name, BigDecimal balance) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.balance = balance;
    }

}
