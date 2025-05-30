package com.libms.services;

import com.libms.dtos.request.BookRequest;
import com.libms.models.Book;
import com.libms.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImplementation implements BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImplementation(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Cacheable(cacheNames = "books", key = "'books'")
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "book", key = "'book_' + #id")
    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "books", allEntries = true)
    public Book addBook(BookRequest bookRequest) {
        Book book = new Book(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getIsbn());
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(cacheNames = "books", allEntries = true), @CacheEvict(cacheNames = "book", key = "'book_' + #id")})
    public Book updateBook(long id, BookRequest bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(cacheNames = "books", allEntries = true),
            @CacheEvict(cacheNames = "book", key = "'book_' + #id")
    })
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

}