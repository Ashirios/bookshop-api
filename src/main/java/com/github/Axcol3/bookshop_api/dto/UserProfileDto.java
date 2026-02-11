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
public class UserProfileDto {

    private String username;
    private String email;
    private BigDecimal balance;

}
