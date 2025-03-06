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

import java.time.LocalDate;

/**
 * Constants for tests to avoid duplication across test classes.
 * Contains common test data values and test helper utilities.
 */
public final class TestConstants {
    
    private TestConstants() {
        // Utility class, no instances allowed
    }
    
    /** Common test age */
    public static final int AGE = 25;
    
    /** Common test birth date based on AGE years ago from now */
    public static final LocalDate BIRTH_DATE = LocalDate.of(
            LocalDate.now().getYear() - AGE, 1, 1);
            
    /** Common test first name */
    public static final String FIRST_NAME = "John";
    
    /** Common test last name */
    public static final String LAST_NAME = "Doe";
    
    /** Common full name (derived from first and last names) */
    public static final String FULL_NAME = FIRST_NAME + " " + LAST_NAME;
    
    /** Date far in the past for senior tests */
    public static final LocalDate SENIOR_BIRTH_DATE = LocalDate.of(
            LocalDate.now().getYear() - 70, 1, 1);
            
    /** Date just over youth cutoff */
    public static final LocalDate YOUTH_BIRTH_DATE = LocalDate.of(
            LocalDate.now().getYear() - 16, 1, 1);
            
    /**
     * Creates a test person with standard values
     * 
     * @return a Person with default test values
     */
    public static Person createTestPerson() {
        return new Person(FIRST_NAME, LAST_NAME, BIRTH_DATE);
    }
    
    /**
     * Creates a young person for testing youth-specific logic
     * 
     * @return a Person who is under 18 years old
     */
    public static Person createYouthPerson() {
        return new Person(FIRST_NAME, LAST_NAME, YOUTH_BIRTH_DATE);
    }
    
    /**
     * Creates a senior person for testing senior-specific logic
     * 
     * @return a Person who is 65+ years old
     */
    public static Person createSeniorPerson() {
        return new Person(FIRST_NAME, LAST_NAME, SENIOR_BIRTH_DATE);
    }
}