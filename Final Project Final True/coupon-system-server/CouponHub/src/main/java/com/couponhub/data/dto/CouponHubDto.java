package com.couponhub.data.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponHubDto {
    private UUID uuid;
    private UUID companyUuid;
    private int category;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String image;
    private int amount;
    private String description;
    private BigDecimal price;
}
