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

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PersonLifecycleTest {

    private static final int AGE = 25;
    private static final LocalDate BIRTH_DATE = LocalDate.of(
            LocalDate.now().getYear() - AGE, 1, 1);
    private static int testCount = 0;
    private Person person;

    @BeforeAll
    public static void setupAll() {
        System.out.println("==== Starting Person Tests ====");
        testCount = 0;
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("==== All Person Tests Completed ====");
        System.out.println("Total tests run: " + testCount);
    }

    @BeforeEach
    public void setup() {
        person = new Person("John", "Doe", BIRTH_DATE);
        testCount++;
        System.out.println("Running test #" + testCount);
    }

    @Test
    public void testFullName() {
        assertEquals("John Doe", person.getFullName());
    }

    @Test
    public void testIsAdult() {
        assertTrue(person.isAdult());
    }

    @Test
    public void testEquals() {
        Person samePerson = new Person("John", "Doe", BIRTH_DATE);
        Person differentPerson = new Person("Jane", "Doe", BIRTH_DATE);

        assertEquals(person, samePerson);
        assertNotEquals(person, differentPerson);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Test #" + testCount + " completed");
        person = null;
    }
}
