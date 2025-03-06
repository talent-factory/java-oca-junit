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

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Represents a person with basic demographic information.
 * Uses Lombok annotations to reduce boilerplate code.
 */
@Data
@Builder
public class Person {

    @NonNull
    private String firstName;
    
    @NonNull
    private String lastName;
    
    private LocalDate birthDate;

    /**
     * Default constructor that initializes a person with empty name and null birthdate
     */
    public Person() {
        this("", "", null);
    }

    /**
     * Parameterized constructor with validation
     * 
     * @param firstName the person's first name
     * @param lastName the person's last name
     * @param birthDate the person's birth date
     */
    public Person(String firstName, String lastName, LocalDate birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);
    }

    /**
     * Calculates the person's age based on their birth date
     * 
     * @return the person's age in years
     * @throws IllegalStateException if birth date is not set
     */
    public int getAge() {
        if (birthDate == null) {
            throw new IllegalStateException("Birth date not set");
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    /**
     * Returns the person's full name (first name + last name)
     * 
     * @return combined first and last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Determines if the person is 18 years or older
     * 
     * @return true if the person is an adult (18+), false otherwise
     */
    public boolean isAdult() {
        return getAge() >= 18;
    }

    /**
     * Sets the first name with validation
     * 
     * @param firstName the person's first name
     * @throws IllegalArgumentException if firstName is null
     */
    public void setFirstName(String firstName) {
        this.firstName = Objects.requireNonNull(firstName, "First name cannot be null");
    }

    /**
     * Sets the last name with validation
     * 
     * @param lastName the person's last name
     * @throws IllegalArgumentException if lastName is null
     */
    public void setLastName(String lastName) {
        this.lastName = Objects.requireNonNull(lastName, "Last name cannot be null");
    }

    /**
     * Sets the birth date with validation
     * 
     * @param birthDate the person's birth date
     * @throws IllegalArgumentException if birthDate is null or in the future
     */
    public void setBirthDate(LocalDate birthDate) {
        if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date must be in the past");
        }
        this.birthDate = birthDate;
    }
}
