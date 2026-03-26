package com.github.Ashirios.bookshop_api.entity;

import java.util.Set;

import com.github.Ashirios.bookshop_api.entity.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

@Id
@GeneratedValue
private Long id;
private String username;
private String email;
private String password;

@ManyToMany
private Set<Book> library;
private Long balance;
private Role role;

}