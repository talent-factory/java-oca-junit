# CLAUDE.md - Java JUnit Project Guidelines

## Build Commands
```bash
./gradlew build            # Build project
./gradlew test             # Run all tests
./gradlew test --tests "edu.PersonTest"             # Run specific test class
./gradlew test --tests "edu.PersonTest.testGetAge"  # Run specific test method
./gradlew test --tests "edu.PersonTestSuite"        # Run test suite
```

## Code Style Guidelines
- **License**: MIT License header required on all files
- **Package Structure**: Single `edu` package for all classes
- **Class Names**: PascalCase (e.g., `Person`, `PersonService`)
- **Method Names**: camelCase (e.g., `getFullName`, `isAdult`)
- **Test Methods**: camelCase with descriptive names, use `@DisplayName` for clarity
- **Constants**: `UPPER_SNAKE_CASE` (e.g., `BIRTH_DATE`)
- **Imports**: Use static imports for assertions (`import static org.junit.jupiter.api.Assertions.*`)
- **Test Structure**: Follow Arrange-Act-Assert pattern
- **Error Handling**: Validate input parameters, throw `IllegalArgumentException` with descriptive messages
- **Annotations**: Use Lombok (`@Data`) to reduce boilerplate
- **Testing**: Use JUnit 5 (Jupiter) and Mockito for mocking