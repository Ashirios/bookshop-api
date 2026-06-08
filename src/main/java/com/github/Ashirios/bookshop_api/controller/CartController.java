package com.github.Ashirios.bookshop_api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.Ashirios.bookshop_api.entity.Book;
import com.github.Ashirios.bookshop_api.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    
    @GetMapping
    public ResponseEntity<List<Book>> getCart(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(cartService.getCart(user.getUsername()));
    }

   
    @PostMapping("/{bookId}")
    public ResponseEntity<List<Book>> addBook(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long bookId) {
        return ResponseEntity.ok(cartService.addBook(user.getUsername(), bookId));
    }

   
    @DeleteMapping("/{bookId}")
    public ResponseEntity<List<Book>> removeBook(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable Long bookId) {
        return ResponseEntity.ok(cartService.removeBook(user.getUsername(), bookId));
    }

    
    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails user) {
        cartService.clearCart(user.getUsername());
        return ResponseEntity.noContent().build();
    }
}
