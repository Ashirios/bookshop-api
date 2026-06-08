package com.github.Ashirios.bookshop_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.Ashirios.bookshop_api.dto.BookStoreDto;
import com.github.Ashirios.bookshop_api.entity.Author;
import com.github.Ashirios.bookshop_api.entity.Book;
import com.github.Ashirios.bookshop_api.repository.AuthorRepository;
import com.github.Ashirios.bookshop_api.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public Book getById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public Book create(BookStoreDto dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(author);
        book.setPrice(dto.getPrice());
        book.setGenres(dto.getGenres());
        book.setPageCount(dto.getPageCount());
        book.setPublicationYear(dto.getPublicationYear());
        return bookRepository.save(book);
    }

    public Book update(Long id, BookStoreDto dto) {
        Book book = getById(id);
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException("Author not found"));
        book.setTitle(dto.getTitle());
        book.setAuthor(author);
        book.setPrice(dto.getPrice());
        book.setGenres(dto.getGenres());
        book.setPageCount(dto.getPageCount());
        book.setPublicationYear(dto.getPublicationYear());
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
