package com.github.Ashirios.bookshop_api.entity;

import com.github.Ashirios.bookshop_api.entity.enums.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;






@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false)
    private Author author;
    
    @Column(nullable = false)
    private BigDecimal price;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<Genre> genres;

    private int pageCount;
    private int publicationYear;
    private Double rating;

}
