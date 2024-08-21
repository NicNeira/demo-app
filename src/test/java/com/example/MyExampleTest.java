package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MyExampleTest {

    @Test
    public void testSumPositiveNumbers() {
        MyService service = new MyService();
        int result = service.sum(2, 3);
        assertEquals(5, result, "2 + 3 should equal 5");
    }

    @Test
    public void testSumNegativeNumbers() {
        MyService service = new MyService();
        int result = service.sum(-2, -3);
        assertEquals(-5, result, "-2 + -3 should equal -5");
    }

    @Test
    public void testSumZero() {
        MyService service = new MyService();
        int result = service.sum(0, 0);
        assertEquals(0, result, "0 + 0 should equal 0");
    }

    @Test
    public void testSumPositiveAndNegative() {
        MyService service = new MyService();
        int result = service.sum(2, -3);
        assertEquals(-1, result, "2 + -3 should equal -1");
    }
}