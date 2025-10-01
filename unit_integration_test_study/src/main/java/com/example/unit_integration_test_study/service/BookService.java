package com.example.unit_integration_test_study.service;

import com.example.unit_integration_test_study.dto.BookRequest;
import com.example.unit_integration_test_study.dto.BookResponse;
import com.example.unit_integration_test_study.entity.Book;
import com.example.unit_integration_test_study.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    public BookResponse createBook(BookRequest request) {
        logger.info("도서 생성 요청: {}", request.getTitle());

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());
        book.setCategory(request.getCategory());

        Book savedBook = bookRepository.save(book);
        logger.info("도서 생성 완료: ID={}", savedBook.getId());

        return BookResponse.from(savedBook);
    }

    @Transactional(readOnly = true)
    public BookResponse getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을 수 없습니다: " + id));

        return BookResponse.from(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category)
                .stream()
                .map(BookResponse::from)
                .collect(Collectors.toList());
    }

    public BookResponse updateStock(Long id, int quantity) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을 수 없습니다: " + id));

        book.reduceStock(quantity);
        Book updatedBook = bookRepository.save(book);

        logger.info("재고 업데이트: 도서 ID={}, 차감량={}, 남은 재고={}",
                id, quantity, updatedBook.getStock());

        return BookResponse.from(updatedBook);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword)
                .stream()
                .map(BookResponse::from)
                .collect(Collectors.toList());
    }

    // 비즈니스 로직 메서드
    public BigDecimal calculateBulkDiscountPrice(List<Long> bookIds, BigDecimal discountRate) {
        List<Book> books = bookRepository.findAllById(bookIds);

        return books.stream()
                .map(book -> book.calculateDiscountPrice(discountRate))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(readOnly = true)
    public boolean hasAvailableStock() {
        return bookRepository.findByStockGreaterThan(0).size() > 0;
    }
}