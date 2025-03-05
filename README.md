# JUnit 5 Testing Tutorial with Person Class

This project demonstrates how to use JUnit 5 to test a simple Person class in Java.

## Project Structure

```
/<project-root>
├── build.gradle
├── README.md
└── src/
    ├── main/
    │   └── java/
    │       └── edu/
    │           ├── Person.java                   - A simple class representing a person
    │           ├── PersonRepository.java         - An interface for a repository that stores Person objects
    │           └── PersonService.java            - A service class that uses the Person class
    └── test/
        └── java/
            └── edu/
                ├── PersonLifecycleTest.java      - Demonstrates JUnit 5 lifecycle annotations
                ├── PersonParameterizedTest.java  - Shows how to use parameterized tests
                ├── PersonServiceTest.java        - Demonstrates mocking with Mockito
                ├── PersonTest.java               - Basic tests for the Person class
                └── PersonTestSuite.java          - A test suite that combines all the test classes
```


## Setup

This is a Gradle project. To run the tests, you need:

1. JDK 11 or higher
2. Gradle 8.10 or higher

## Running the Tests

### Using Gradle

To run all tests:
```
./gradlew test
```


To run a specific test class:
```
./gradlew test --tests "PersonTest"
```
