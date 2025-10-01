package com.example.unit_integration_test_study.unit;

import com.example.unit_integration_test_study.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


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

        assertThat(book.getStock()).isEqualTo(5);
    }


}