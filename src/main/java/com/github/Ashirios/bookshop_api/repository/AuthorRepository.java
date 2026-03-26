package com.github.Ashirios.bookshop_api.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.Ashirios.bookshop_api.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {


   

    Optional<Author> findByName(String name);
    
    public Set<Author> findByBooks_TitleContainingIgnoreCase(String title);


}
