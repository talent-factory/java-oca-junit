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

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Person data access.
 * Provides methods to find and store Person objects.
 */
public interface PersonRepository {

    /**
     * Finds a person by their full name
     * 
     * @param fullName the full name to search for
     * @return the found Person or null if not found
     */
    Person findByFullName(String fullName);
    
    /**
     * Saves a person to the repository
     * 
     * @param person the person to save
     * @throws IllegalArgumentException if person is null
     */
    void save(Person person);
    
    /**
     * Finds a person by their ID
     * 
     * @param id the ID to search for
     * @return an Optional containing the person if found, or empty Optional if not found
     */
    Optional<Person> findById(Long id);
    
    /**
     * Retrieves all persons from the repository
     * 
     * @return a list of all persons in the repository
     */
    List<Person> findAll();
    
    /**
     * Deletes a person from the repository
     * 
     * @param person the person to delete
     */
    void delete(Person person);
}
