package com.github.Axcol3.bookshop_api.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class User {

@Id
@GeneratedValue
private Long id;
private String username;
private String email;
private String password;
@ElementCollection
private Set<Book> ownedBooks;
private Long balance;

}