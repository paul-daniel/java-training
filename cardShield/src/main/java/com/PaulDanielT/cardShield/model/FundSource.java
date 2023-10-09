package com.PaulDanielT.cardShield.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundSource {
    private Integer fundSourceId;
    private String cardNumber;
    private Date expiryDate;
    private String cardType;
    private String cardHolderName;
}
