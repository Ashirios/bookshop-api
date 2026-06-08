package com.github.Ashirios.bookshop_api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.Ashirios.bookshop_api.entity.Book;
import com.github.Ashirios.bookshop_api.entity.User;
import com.github.Ashirios.bookshop_api.repository.BookRepository;
import com.github.Ashirios.bookshop_api.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

   
    public List<Book> getCart(String username) {
        User user = findUser(username);
        return new ArrayList<>(user.getLibrary());
    }

    @Transactional
    public List<Book> addBook(String username, Long bookId) {
        User user = findUser(username);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + bookId));
        user.getLibrary().add(book);
        userRepository.save(user);
        return new ArrayList<>(user.getLibrary());
    }

    @Transactional
    public List<Book> removeBook(String username, Long bookId) {
        User user = findUser(username);
        user.getLibrary().removeIf(b -> b.getId().equals(bookId));
        userRepository.save(user);
        return new ArrayList<>(user.getLibrary());
    }

    @Transactional
    public void clearCart(String username) {
        User user = findUser(username);
        user.getLibrary().clear();
        userRepository.save(user);
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + username));
    }
}
