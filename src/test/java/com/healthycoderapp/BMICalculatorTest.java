package com.healthycoderapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BMICalculatorTest {

    @Test
    void should_ReturnTrue_When_DietRecommended() {
        // Given
        double weight = 89.0;
        double height = 1.72;

        // When
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // Then
        assertTrue(recommended);
    }

    @Test
    void should_ReturnFalse_When_DietRecommended() {
        // Given
        double weight = 50.0;
        double height = 1.92;

        // When
        boolean recommended = BMICalculator.isDietRecommended(weight, height);

        // Then
        assertFalse(recommended);
    }
}
