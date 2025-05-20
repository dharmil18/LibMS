package com.libms.services;

import com.libms.dtos.request.BookRequest;
import com.libms.models.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    public List<Book> getBooks();

    public Optional<Book> getBookById(long id);

    public Book addBook(BookRequest bookRequest);

    public Book updateBook(long id, BookRequest bookRequest);

    public void deleteBook(long id);
}
