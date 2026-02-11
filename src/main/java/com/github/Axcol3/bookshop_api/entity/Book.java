package com.github.Axcol3.bookshop_api.entity;

import com.github.Axcol3.bookshop_api.entity.enums.Genre;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;
    private int pageCount;
    private int publicationYear;

}
