package com.example.unit_integration_test_study.controller;

import com.example.unit_integration_test_study.dto.BookRequest;
import com.example.unit_integration_test_study.dto.BookResponse;
import com.example.unit_integration_test_study.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        BookResponse response = bookService.createBook(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBook(@PathVariable Long id) {
        BookResponse response = bookService.getBook(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks(
            @RequestParam(required = false) String category) {

        List<BookResponse> books = category != null
                ? bookService.getBooksByCategory(category)
                : bookService.getAllBooks();

        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooks(
            @RequestParam String keyword) {
        List<BookResponse> books = bookService.searchBooks(keyword);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<BookResponse> updateStock(
            @PathVariable Long id,
            @RequestParam int quantity) {

        try {
            BookResponse response = bookService.updateStock(id, quantity);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkAvailability() {
        boolean hasStock = bookService.hasAvailableStock();
        return ResponseEntity.ok(hasStock);
    }
}