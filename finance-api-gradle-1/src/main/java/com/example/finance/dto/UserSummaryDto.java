package com.example.finance.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
@Data
public class UserSummaryDto {
    private UUID id;
    private String name;
    private BigDecimal balance;

    public UserSummaryDto(UUID id, String name, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }


}
