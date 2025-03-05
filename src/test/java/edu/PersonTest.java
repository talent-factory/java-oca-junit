/*
 * Released under MIT License
 *
 * Copyright (©) 2025. Talent Factory GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package edu;

import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    public static final int AGE = 25;
    // Dies ist das zu testende Objekt
    private Person person;

    @BeforeEach
    void setUp() {

        // Neues Objekt instanziieren und Werte zuweisen via Konstruktor.
        int year = LocalDate.now().getYear() - AGE;
        person = new Person("John", "Doe", LocalDate.of(year, 1, 1));
    }

    @Test
    void testGetFirstName() {
        String expected = "John";
        assertEquals(expected, person.getFirstName());
    }

    @Test
    void testGetLastName() {
        String expected = "Doe";
        assertEquals(expected, person.getLastName());
    }

    @Test
    void testGetAge() {
        assertEquals(AGE, person.getAge());
    }

    @Test
    void testGetFillName() {
        String expected = "John Doe";
        assertEquals(expected, person.getFullName());
    }

    @Test
    void testIsAdult() {
        boolean expected = true;
        assertEquals(expected, person.isAdult());
    }

    @Test
    @DisplayName("setBirthDate(null)")
    void whenExceptionThrown_thenAssertionSucceeds() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            person.setBirthDate(null);
        });

        String expectedMessage = "Birth date must be in the past";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("setBirthDate(Future)")
    void whenExceptionThrown() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            int futureYear = LocalDate.now().getYear() + 1;
            person.setBirthDate(LocalDate.of(futureYear, 1, 1));
        });

        String expectedMessage = "Birth date must be in the past";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }


}
