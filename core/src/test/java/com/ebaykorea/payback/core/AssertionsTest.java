package com.ebaykorea.payback.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class AssertionsTest {

    @Test
    void standardAssertions() {
        int a = 1 , b = 2;
        assertEquals(2, b);
        assertEquals(4, b+a+a);
        assertTrue(a < b);
    }

    @Test
    void groupedAssertions() {
        String firstname = "Jane", Lastname = "Doe";
        assertAll("person",
                () -> assertEquals("Jane", firstname),
                () -> assertEquals("Doe", Lastname));
    }

    @Test
    void exceptionTesting() {
        NumberFormatException thrown
                = Assertions.assertThrows(NumberFormatException.class, () -> {
            Integer.parseInt("One");
        }, "NumberFormatException was expected");
        Assertions.assertEquals("For input string: \"One\"", thrown.getMessage());
    }

    @Test
    void timeoutNotExceeded() {
        assertTimeout(Duration.ofSeconds(5), () -> {
        });
    }

    @Test
    void timeoutNotExceededWithResult() {
        String actualResult = assertTimeout(Duration.ofSeconds(10), () -> {
            return "timeoutNotExceededWithResult";
        });
        assertEquals("timeoutNotExceededWithResult", actualResult);
    }

    @Test
    void timeoutExceeded() {
        //8초후 에러발생
        assertTimeout(Duration.ofSeconds(3), () -> {
            Thread.sleep(8000);
            System.out.println("timeoutExceeded");
        });
    }

    @Test
    void timeoutExceededWithPreemptiveTermination() {
        //3초후 에러발생
        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
            Thread.sleep(8000);
            System.out.println("timeoutExceededWithPreemptive");
        });
    }
}