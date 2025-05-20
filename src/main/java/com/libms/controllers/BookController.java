package com.libms.controllers;

import java.util.List;
import java.util.Optional;

import com.libms.dtos.request.BookRequest;
import com.libms.models.Book;
import com.libms.services.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Book Controller", description = "APIs for managing books in the library")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Todo: Implement the getAllBooks method
    @GetMapping("/books")
    @Operation(summary = "Get all books", description = "Returns a list of all books in the library")
    @ApiResponse(description = "Returns a ResponseEntity containing List of Books")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getBooks();
        return ResponseEntity.ok(books);
    }


    // Todo: Implement the getBookById method
    @GetMapping("/books/{id}")
    @Operation(summary = "Get a book by ID", description = "Returns a book with the specified ID")
    @Parameter(name = "id", description = "id of the book")
    @ApiResponse(description = "Returns a book with the id")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }


    // Todo: Implement the addBook method
    @PostMapping("/books")
    @Operation(summary = "Add a new book", description = "Adds a new book to the library")
    @Parameter(name = "bookRequest", description = "BookRequest object containing book details")
    @ApiResponse(description = "Returns the created book")
    public ResponseEntity<Book> addBook(@Valid @RequestBody BookRequest bookRequest) {
        Book savedBook = bookService.addBook(bookRequest);
        return ResponseEntity.status(201).body(savedBook);
    }

    // Todo: Implement the updateBook method
    @PutMapping("/books/{id}")
    @Operation(summary = "Update a book", description = "Updates the details of an existing book")
    @Parameter(name = "id", description = "id of the book")
    @Parameter(name = "bookRequest", description = "BookRequest object containing updated book details")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @Valid @RequestBody BookRequest bookRequest) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            Book updatedBook = bookService.updateBook(id, bookRequest);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Todo: Implement the deleteBook method
    @DeleteMapping("/books/{id}")
    @Operation(summary = "Delete a book", description = "Deletes a book with the specified ID")
    @Parameter(name = "id", description = "id of the book")
    @ApiResponse(description = "void")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            bookService.deleteBook(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
