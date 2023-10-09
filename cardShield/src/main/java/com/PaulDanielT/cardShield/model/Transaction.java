package com.PaulDanielT.cardShield.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Integer transactionId;
    private Integer cardId;
    private BigDecimal amount;
    private Date transactionDate;
    private Integer vendorId;
    private Integer transactionMonth;
    private Integer transactionYear;
}
