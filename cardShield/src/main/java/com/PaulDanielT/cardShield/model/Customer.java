package com.PaulDanielT.cardShield.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Integer customerId;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String country;
    private String password;
    private Integer fundSourceId;
}
