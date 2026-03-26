package com.github.Ashirios.bookshop_api.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderMessageDto {
   private Long orderId;
    private Long userId;
    private Integer bookCount;
    private BigDecimal totalPrice;
    private String timestamp;
    
}
