package com.github.Axcol3.bookshop_api.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookStoreDto {

    private String title;
    private String author;
    private BigDecimal price;
    
}
