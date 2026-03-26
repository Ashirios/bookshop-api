package com.github.Ashirios.bookshop_api.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long userId;
    private BigDecimal totalPrice;
    private List<Long> bookIds; 
}
