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

import lombok.Data;

import java.time.LocalDate;

@Data
@SuppressWarnings("all")
public class Person {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;


    // Null-Argument Konstruktor
    public Person() {
        this("", "", null);
    }

    // Parameterized constructor
    public Person(String firstName, String lastName, LocalDate birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);// Using setter for validation
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    // Return full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Is the person an adult?
    public boolean isAdult() {
        return getAge() >= 18;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date must be in the past");
        }
        this.birthDate = birthDate;
    }
}
