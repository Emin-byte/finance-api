package com.example.finance.dto;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
@Data
public class TransactionDto {
    private UUID id;
    private BigDecimal amount;
    private OffsetDateTime createdAt;
    private UserSummaryDto user;

    public TransactionDto(UUID id, BigDecimal amount, OffsetDateTime createdAt, UserSummaryDto user) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        this.user = user;
    }


}
