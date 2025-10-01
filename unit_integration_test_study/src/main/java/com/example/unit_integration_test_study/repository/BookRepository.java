package com.example.unit_integration_test_study.repository;

import com.example.unit_integration_test_study.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByCategory(String category);

    List<Book> findByStockGreaterThan(Integer stock);

    @Query("SELECT b FROM Book b WHERE b.price BETWEEN :minPrice AND :maxPrice")
    List<Book> findByPriceRange(@Param("minPrice") BigDecimal minPrice,
                                @Param("maxPrice") BigDecimal maxPrice);

    List<Book> findByTitleContainingIgnoreCase(String title);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.stock = 0")
    long countOutOfStockBooks();
}