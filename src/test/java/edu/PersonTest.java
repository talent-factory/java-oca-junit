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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Person class.
 * Demonstrates various JUnit 5 testing features and patterns.
 */
@DisplayName("Person Class Tests")
class PersonTest {

    private Person person;

    @BeforeEach
    void setUp() {
        // Arrange - common test setup
        person = new Person("John", "Doe", TestConstants.BIRTH_DATE);
    }

    @Nested
    @DisplayName("Basic Property Tests")
    class BasicPropertyTests {
        
        @Test
        @DisplayName("Test getFirstName()")
        void testGetFirstName() {
            // Assert
            assertEquals("John", person.getFirstName(), "First name should match constructor argument");
        }
    
        @Test
        @DisplayName("Test getLastName()")
        void testGetLastName() {
            // Assert
            assertEquals("Doe", person.getLastName(), "Last name should match constructor argument");
        }
    
        @Test
        @DisplayName("Test getAge()")
        void testGetAge() {
            // Assert
            assertEquals(TestConstants.AGE, person.getAge(), "Age should be calculated correctly from birth date");
        }
    
        @Test
        @DisplayName("Test getFullName()")
        void testGetFullName() {
            // Assert
            assertEquals("John Doe", person.getFullName(), "Full name should be first name + space + last name");
        }
    
        @Test
        @DisplayName("Test isAdult() for adult")
        void testIsAdult() {
            // Assert
            assertTrue(person.isAdult(), "Person with age 25 should be considered an adult");
        }
    }

    @Nested
    @DisplayName("Setter Tests")
    class SetterTests {
        
        @Test
        @DisplayName("Test setFirstName()")
        void testSetFirstName() {
            // Act
            person.setFirstName("Jane");
            
            // Assert
            assertEquals("Jane", person.getFirstName(), "First name should be updated");
        }
        
        @Test
        @DisplayName("Test setLastName()")
        void testSetLastName() {
            // Act
            person.setLastName("Smith");
            
            // Assert
            assertEquals("Smith", person.getLastName(), "Last name should be updated");
        }
        
        @Test
        @DisplayName("Test setFirstName(null) throws exception")
        void testSetFirstNameNull() {
            // Act & Assert
            assertThrows(NullPointerException.class, 
                    () -> person.setFirstName(null),
                    "Setting null first name should throw NullPointerException");
        }
        
        @Test
        @DisplayName("Test setLastName(null) throws exception")
        void testSetLastNameNull() {
            // Act & Assert
            assertThrows(NullPointerException.class, 
                    () -> person.setLastName(null),
                    "Setting null last name should throw NullPointerException");
        }
    }

    @Nested
    @DisplayName("Birth Date Validation Tests")
    class BirthDateValidationTests {
        
        @Test
        @DisplayName("setBirthDate(null) validation")
        void testSetBirthDateNull() {
            // Act
            person.setBirthDate(null);
            
            // Assert
            assertThrows(IllegalStateException.class, 
                    () -> person.getAge(),
                    "Getting age with null birth date should throw IllegalStateException");
        }
    
        @Test
        @DisplayName("setBirthDate(Future) validation")
        void testSetBirthDateFuture() {
            // Arrange
            LocalDate futureBirthDate = LocalDate.now().plusDays(1);
            
            // Act & Assert
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                    () -> person.setBirthDate(futureBirthDate),
                    "Setting future birth date should throw IllegalArgumentException");
    
            // Additional assertions on the exception message
            String expectedMessage = "Birth date must be in the past";
            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(expectedMessage), 
                    "Exception message should mention that birth date must be in the past");
        }
    }

    @Test
    @DisplayName("Builder pattern creates valid person")
    void testBuilderPattern(TestInfo testInfo) {
        // Arrange & Act
        Person builtPerson = Person.builder()
                .firstName("Built")
                .lastName("Person")
                .birthDate(TestConstants.BIRTH_DATE)
                .build();
        
        // Assert
        assertAll(
            () -> assertEquals("Built", builtPerson.getFirstName()),
            () -> assertEquals("Person", builtPerson.getLastName()),
            () -> assertEquals(TestConstants.BIRTH_DATE, builtPerson.getBirthDate()),
            () -> assertEquals(TestConstants.AGE, builtPerson.getAge())
        );
    }
}
