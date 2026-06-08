package com.github.Ashirios.bookshop_api.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.github.Ashirios.bookshop_api.entity.enums.Genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BookStoreDto {
    @NotBlank
    private String title;

    @NotNull
    private Long authorId;

    @NotNull
    @Positive
    private BigDecimal price;

    private Set<Genre> genres;
    private int pageCount;
    private int publicationYear;
}
