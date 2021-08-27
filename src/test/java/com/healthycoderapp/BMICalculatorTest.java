package com.healthycoderapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BMICalculatorTest {


    @Test
    void should_ReturnTrue_When_DietRecommended() {
        assertTrue(BMICalculator.isDietRecommended(89.0, 1.72));
    }
}
