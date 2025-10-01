package com.example.unit_integration_test_study.dto;

import com.example.unit_integration_test_study.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private boolean inStock;
    private LocalDateTime createdAt;

    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getStock(),
                book.getCategory(),
                book.isInStock(),
                book.getCreatedAt()
        );
    }
}