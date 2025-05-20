package com.libms.services;

import com.libms.dtos.request.BookRequest;
import com.libms.models.Book;
import com.libms.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book addBook(BookRequest bookRequest) {
        Book book = new Book(bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getIsbn());
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(long id, BookRequest bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

}