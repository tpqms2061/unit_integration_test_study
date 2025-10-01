package com.example.unit_integration_test_study.unit;

import com.example.unit_integration_test_study.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class BookCalculatorTest {

    @Test
    void testDiscountPriceCalculation() {

        Book book = new Book();
        book.setPrice(new BigDecimal("10000"));
        BigDecimal discountRate = new BigDecimal("0.1");

        BigDecimal discountPrice = book.calculateDiscountPrice(discountRate);

        assertThat(discountPrice).isEqualByComparingTo("9000");
    }

    @Test
    void testStockReduction() {
        Book book = new Book();
        book.setStock(10);

        book.reduceStock(3);

        assertThat(book.getStock()).isEqualTo(7);
    }

    @Test
    void testInsufficientException() {
        Book book = new Book();
        book.setStock(2);
        assertThatThrownBy(() -> book.reduceStock(5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("재고가 부족합니다");
    }

    @Test
    void testStockStatusCheck() {
        Book insStockBook = new Book();
        insStockBook.setStock(5);

        Book outofStockBook = new Book();
        outofStockBook.setStock(0);

        assertThat(insStockBook.isInStock()).isTrue();
        assertThat(outofStockBook.isInStock()).isFalse();
    }


}