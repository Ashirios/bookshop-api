package com.github.Ashirios.bookshop_api.dto;

import java.util.Set;

import com.github.Ashirios.bookshop_api.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorProfileDto {
    private Long id;
    private String name;
    private Set<Book> books;
}
