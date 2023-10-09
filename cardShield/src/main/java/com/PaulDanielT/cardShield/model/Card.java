package com.PaulDanielT.cardShield.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    private Integer cardId;
    private Integer customerId;
    private BigDecimal spendingLimit;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String cardNumber;
    private Date expirationDate;
    private String cvv;
    private Integer categoryId;
}