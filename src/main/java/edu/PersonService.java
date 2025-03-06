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

import java.util.Objects;

/**
 * Service class for Person-related operations.
 * Provides methods to find, save, and check eligibility of persons.
 */
public class PersonService {

    /** Minimum age for senior discount */
    public static final int SENIOR_AGE = 65;
    
    /** Maximum age for youth discount */
    public static final int YOUTH_AGE = 18;

    private final PersonRepository repository;

    /**
     * Constructs a PersonService with the specified repository
     * 
     * @param repository the repository to use for person data access
     * @throws IllegalArgumentException if repository is null
     */
    public PersonService(PersonRepository repository) {
        this.repository = Objects.requireNonNull(repository, "Repository cannot be null");
    }

    /**
     * Finds a person by their full name
     * 
     * @param fullName the full name to search for
     * @return the found Person or null if not found
     */
    public Person findByFullName(String fullName) {
        return repository.findByFullName(fullName);
    }

    /**
     * Saves a person to the repository
     * 
     * @param person the person to save
     * @throws IllegalArgumentException if person is null
     */
    public void savePerson(Person person) {
        Objects.requireNonNull(person, "Person cannot be null");
        repository.save(person);
    }

    /**
     * Checks if a person is eligible for age-based discount
     * People under 18 or over 65 get a discount
     * 
     * @param fullName the full name of the person to check
     * @return true if eligible for discount, false otherwise
     */
    public boolean isPersonEligibleForDiscount(String fullName) {
        Person person = repository.findByFullName(fullName);
        if (person == null) {
            return false;
        }

        return person.getAge() < YOUTH_AGE || person.getAge() >= SENIOR_AGE;
    }
}
