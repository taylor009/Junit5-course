package com.healthycoderapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void should_ThrowArithmeticException_When_HeightZero() {
        // Given
        double weight = 50.0;
        double height = 0.0;

        // When
        Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

        // Then
        assertThrows(ArithmeticException.class, executable);
    }

    @Test
    void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty() {
        // Given
        List<Coder> coders = new ArrayList<>();
        coders.add(new Coder(1.80, 60.0));
        coders.add(new Coder(1.82, 98.0));
        coders.add(new Coder(1.82, 64.7));

        // When
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // Then
        assertAll(
                () -> assertEquals(1.82, coderWorstBMI.getHeight()),
                () -> assertEquals(98.0, coderWorstBMI.getWeight())
        );
    }

    @Test
    void should_ReturnNullWorstBMI_When_CoderListEmpty() {
        // Given
        List<Coder> coders = new ArrayList<>();

        // When
        Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

        // Then
        assertNull(coderWorstBMI);
    }
}
