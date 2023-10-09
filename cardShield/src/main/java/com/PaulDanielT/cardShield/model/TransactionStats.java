package com.PaulDanielT.cardShield.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStats {
    private Integer customerId;
    private BigDecimal dailySpending;
    private BigDecimal weeklySpending;
    private BigDecimal monthlySpending;
}
