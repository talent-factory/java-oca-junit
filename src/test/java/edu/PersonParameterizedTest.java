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

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PersonParameterizedTest {

    // Method source for the parameterized test
    private static Stream<Arguments> createTestPersonsAndAges() {
        return Stream.of(
                Arguments.of("John", "Doe", 25, true),
                Arguments.of("Jane", "Smith", 17, false),
                Arguments.of("Bob", "Johnson", 18, true),
                Arguments.of("Alice", "Brown", 16, false)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {18, 21, 30, 65, 100})
    public void testIsAdult_WhenAdult(int age) {
        LocalDate birthDate = LocalDate.of(LocalDate.now().getYear() - age, 1, 1);
        Person person = new Person("Test", "Person", birthDate);
        assertTrue(person.isAdult());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 10, 15, 17})
    public void testIsAdult_WhenNotAdult(int age) {
        LocalDate birthDate = LocalDate.of(LocalDate.now().getYear() - age, 1, 1);
        Person person = new Person("Test", "Person", birthDate);
        assertFalse(person.isAdult());
    }

    @ParameterizedTest
    @CsvSource({
            "John,Doe,John Doe",
            "Alice,Smith,Alice Smith",
            "Bob,Johnson,Bob Johnson"
    })
    public void testFullName(String firstName, String lastName, String expectedFullName) {
        Person person = new Person("Test", "Person", TestConstants.BIRTH_DATE);
        person.setFirstName(firstName);
        person.setLastName(lastName);

        assertEquals(expectedFullName, person.getFullName());
    }

    @ParameterizedTest
    @MethodSource("createTestPersonsAndAges")
    public void testPersonAgeCombinations(String firstName, String lastName, int age, boolean shouldBeAdult) {
        LocalDate birthDate = LocalDate.of(LocalDate.now().getYear() - age, 1, 1);
        Person person = new Person(firstName, lastName, birthDate);

        assertEquals(firstName, person.getFirstName());
        assertEquals(lastName, person.getLastName());
        assertEquals(age, person.getAge());
        assertEquals(shouldBeAdult, person.isAdult());
    }

}
