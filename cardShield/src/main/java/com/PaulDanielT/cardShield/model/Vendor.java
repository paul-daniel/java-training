package com.PaulDanielT.cardShield.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {
    private Integer vendorId;
    private String vendorName;
    private String vendorCategory;
}
