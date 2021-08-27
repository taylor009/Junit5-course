package com.healthycoderapp;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class BMICalculatorTest {

    private final String environment = "prod";

    @BeforeAll
    static void beforeAll() {
        // Must be static also performs operations that should be performed exactly once before all unit tests.
        System.out.println("Before all unit tests.");
    }

    @AfterAll
    static void afterAll() {
        // exact opposite of beforeAll
        System.out.println("After all unit tests.");
    }

    @Nested
    class IsDietRecommendedTest {
        @ParameterizedTest(name = "weight={0}, height={1}")
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void should_ReturnTrue_When_DietRecommended(Double coderWeight, Double coderHeight) {
            // Given
            double weight = coderWeight;
            double height = coderHeight;

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
    }

    @Nested
    class FindCoderWithWorstBMITests {
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
        void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderList10000Elements() {
            // given
            assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
            List<Coder> coders = new ArrayList<>();
            for (int i=0; i < 10000; i++) {
                coders.add(new Coder(1.0 + i, 10 + i));
            }
            // when
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
            // then
            assertTimeout(Duration.ofMillis(60), executable);
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

    @Nested
    class GetBMIScoresTests {
        @Test
        void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
            // Given
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));
            double[] expected = {18.52, 29.59, 19.53};

            // When
            double[] bmiScores = BMICalculator.getBMIScores(coders);

            // Then
            assertArrayEquals(expected, bmiScores);
        }
    }
}
