package com.github.Ashirios.bookshop_api.repository;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.Ashirios.bookshop_api.entity.Book;
import com.github.Ashirios.bookshop_api.entity.enums.Genre;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> { 
    

    public Set<Book> findByAuthor_Name(String authorName);

    public Set<Book> findByGenresIn(Set<Genre> genres);

    public Set<Book> findByPublicationYearBetween(int startYear, int endYear);


    public Set<Book> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    public Set<Book> findByRatingGreaterThanEqual(Double rating);

    public Set<Book> findByTitleContainingIgnoreCase(String title);
}
