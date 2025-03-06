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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    private PersonService service;

    @BeforeEach
    public void setUp() {
        service = new PersonService(repository);
    }

    @Test
    public void testFindByFullName() {
        // Arrange
        Person expectedPerson = TestConstants.createTestPerson();
        when(repository.findByFullName("John Doe")).thenReturn(expectedPerson);

        // Act
        Person result = service.findByFullName("John Doe");

        // Assert
        assertEquals(expectedPerson, result);
        verify(repository).findByFullName("John Doe");
    }

    @Test
    public void testSavePerson() {
        // Arrange
        Person person = TestConstants.createTestPerson();

        // Act
        service.savePerson(person);

        // Assert
        verify(repository).save(person);
    }

    @Test
    public void testSaveNullPerson_ThrowsException() {
        // Act & Assert
        Exception exception = assertThrows(NullPointerException.class, 
                () -> service.savePerson(null));
                
        assertEquals("Person cannot be null", exception.getMessage());

        // Verify repository was never called
        verify(repository, never()).save(any());
    }

    @Test
    public void testIsPersonEligibleForDiscount_Young() {
        // Arrange - person under 18
        Person youngPerson = TestConstants.createYouthPerson();
        when(repository.findByFullName("Young Person")).thenReturn(youngPerson);

        // Act
        boolean result = service.isPersonEligibleForDiscount("Young Person");

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsPersonEligibleForDiscount_Adult() {
        // Arrange - person between 18 and 65
        LocalDate birthDate = LocalDate.of(
                LocalDate.now().getYear() - 35, 1, 1);
        Person adultPerson = new Person("Adult", "Person", birthDate);
        when(repository.findByFullName("Adult Person")).thenReturn(adultPerson);

        // Act
        boolean result = service.isPersonEligibleForDiscount("Adult Person");

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsPersonEligibleForDiscount_Senior() {
        // Arrange - person over 65
        Person seniorPerson = TestConstants.createSeniorPerson();
        when(repository.findByFullName("Senior Person")).thenReturn(seniorPerson);

        // Act
        boolean result = service.isPersonEligibleForDiscount("Senior Person");

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsPersonEligibleForDiscount_PersonNotFound() {
        // Arrange - repository returns null
        when(repository.findByFullName("Unknown Person")).thenReturn(null);

        // Act
        boolean result = service.isPersonEligibleForDiscount("Unknown Person");

        // Assert
        assertFalse(result);
    }
}
