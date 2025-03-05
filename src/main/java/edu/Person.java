package edu;

import java.util.Objects;

public class Person {

    private String firstName;
    private String lastName;
    private int age;

    // Default constructor
    public Person() {
        this.firstName = "";
        this.lastName = "";
        this.age = 0;
    }

    // Parameterized constructor
    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        setAge(age);  // Using setter for validation
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }

    // Return full name
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Is the person an adult?
    public boolean isAdult() {
        return age >= 18;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Person person = (Person) obj;

        if (age != person.age) return false;
        if (!Objects.equals(firstName, person.firstName)) return false;
        return Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }
}
