package com.github.Ashirios.bookshop_api.entity;

import java.util.Set;

import com.github.Ashirios.bookshop_api.entity.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String biography;
    @OneToMany(mappedBy = "author")
    private Set<Book> books;
    private Role role;
    

}
