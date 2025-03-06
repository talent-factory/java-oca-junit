# JUnit 5 Tutorial für Java-Entwickler

Dieses Tutorial führt Sie schrittweise in die Verwendung von JUnit 5 für das Testen von Java-Anwendungen ein. Als durchgehendes Beispiel verwenden wir eine einfache `Person`-Klasse, an der wir verschiedene Aspekte des Testframeworks demonstrieren werden.

## Inhaltsverzeichnis

1. [Einführung in das Testen mit JUnit 5](#1-einführung-in-das-testen-mit-junit-5)
2. [Das Beispielprojekt einrichten](#2-das-beispielprojekt-einrichten)
3. [Erste Tests schreiben](#3-erste-tests-schreiben)
4. [Assertions verstehen und anwenden](#4-assertions-verstehen-und-anwenden)
5. [Ausnahmen testen](#5-ausnahmen-testen)
6. [Test-Lebenszyklus mit Annotations](#6-test-lebenszyklus-mit-annotations)
7. [Parametrisierte Tests](#7-parametrisierte-tests)
8. [Mocking mit Mockito](#8-mocking-mit-mockito)
9. [Testsuite organisieren](#9-testsuite-organisieren)
10. [Best Practices für das Testen](#10-best-practices-für-das-testen)

## 1. Einführung in das Testen mit JUnit 5

### Was ist JUnit 5?

JUnit 5 ist das aktuelle Hauptframework für Tests in Java-Anwendungen. Es besteht aus drei Hauptkomponenten:

- **JUnit Platform**: Das Fundament für die Ausführung von Tests auf der JVM
- **JUnit Jupiter**: Die neue Programmiermodell und Erweiterungsmodell für das Schreiben von Tests
- **JUnit Vintage**: Unterstützt rückwärtskompatible Ausführung von Tests, die mit JUnit 3 oder JUnit 4 geschrieben wurden

### Warum Unit-Tests?

Unit-Tests bieten zahlreiche Vorteile für die Softwareentwicklung:

- Frühzeitige Erkennung von Fehlern
- Dokumentation des gewünschten Verhaltens
- Sicherheit bei Refactoring und Ergänzungen
- Verbesserung des Designs, da testbarer Code oft besser strukturiert ist
- Vertrauen in den Code und seine Funktion

## 2. Das Beispielprojekt einrichten

### Die Person-Klasse

Unsere Beispielanwendung dreht sich um eine einfache `Person`-Klasse mit folgenden Eigenschaften:

- Vor- und Nachname
- Geburtsdatum
- Methoden zur Berechnung des Alters
- Methoden zur Prüfung, ob die Person volljährig ist
- Eine Methode, die den vollständigen Namen zurückgibt

Hier ist die Implementation:

```java
@Data
public class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    // Default constructor
    public Person() {
        this("", "", null);
    }
    
    // Parameterized constructor
    public Person(String firstName, String lastName, LocalDate birthDate) {
        setFirstName(firstName);
        setLastName(lastName);
        setBirthDate(birthDate);  // Using setter for validation
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
```

### Gradle-Konfiguration

Um JUnit 5 in unserem Projekt zu verwenden, benötigen wir die richtigen Abhängigkeiten in unserem Gradle-Build-Skript:

```gradle
plugins {
    id 'java'
}

group = 'edu'
version = '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor('org.projectlombok:lombok:1.18.36')
    implementation('org.projectlombok:lombok:1.18.36')

    // JUnit Jupiter dependencies
    testImplementation 'org.junit.jupiter:junit-jupiter:5.11.4'
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.11.4'
    testImplementation 'org.junit.jupiter:junit-jupiter-engine:5.11.4'
    
    // JUnit Platform dependencies for suite support
    testImplementation 'org.junit.platform:junit-platform-suite-api:1.11.4'
    testImplementation 'org.junit.platform:junit-platform-suite-engine:1.11.4'
    
    // Mockito for mocking in tests
    testImplementation 'org.mockito:mockito-junit-jupiter:5.16.0'
}

test {
    useJUnitPlatform()
    testLogging {
        events "passed", "skipped", "failed"
    }
}
```

Diese Konfiguration fügt die benötigten JUnit 5 Jupiter-Komponenten, Lombok für Boilerplate-Reduktion sowie Mockito für Mocking-Tests hinzu.

## 3. Erste Tests schreiben

Lassen Sie uns mit einigen grundlegenden Tests für unsere `Person`-Klasse beginnen.

### Die Grundstruktur eines Tests

Ein JUnit 5-Test besteht aus einer Methode, die mit `@Test` annotiert ist und Assertions enthält, die das erwartete Verhalten überprüfen.

Hier ist ein Beispiel für einen Test des Standardkonstruktors unserer `Person`-Klasse:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    @Test
    public void testDefaultConstructor() {
        // Arrange
        Person person = new Person();
        
        // Assert
        assertEquals("", person.getFirstName());
        assertEquals("", person.getLastName());
        assertThrows(IllegalArgumentException.class, () -> person.getBirthDate());
    }
}
```

### Tests für Konstruktoren und Getter/Setter

Erweitern wir unsere Tests, um den parametrisierten Konstruktor und einige Getter/Setter zu prüfen:

```java
@Test
public void testParameterizedConstructor() {
    // Arrange
    LocalDate birthDate = LocalDate.of(1990, 1, 1);
    Person person = new Person("John", "Doe", birthDate);
    
    // Assert
    assertEquals("John", person.getFirstName());
    assertEquals("Doe", person.getLastName());
    assertEquals(birthDate, person.getBirthDate());
}

@Test
public void testSetFirstName() {
    // Arrange
    Person person = new Person();
    
    // Act
    person.setFirstName("Alice");
    
    // Assert
    assertEquals("Alice", person.getFirstName());
}

@Test
public void testSetBirthDate() {
    // Arrange
    Person person = new Person();
    LocalDate birthDate = LocalDate.of(1990, 1, 1);
    
    // Act
    person.setBirthDate(birthDate);
    
    // Assert
    assertEquals(birthDate, person.getBirthDate());
}
```

## 4. Assertions verstehen und anwenden

JUnit 5 bietet eine Vielzahl von Assertions, um verschiedene Aspekte des Verhaltens Ihres Codes zu testen.

### Häufig verwendete Assertions

- `assertEquals(expected, actual)`: Prüft, ob zwei Werte gleich sind
- `assertTrue(condition)`: Prüft, ob eine Bedingung wahr ist
- `assertFalse(condition)`: Prüft, ob eine Bedingung falsch ist
- `assertNotNull(object)`: Prüft, ob ein Objekt nicht null ist
- `assertThrows(exceptionClass, executable)`: Prüft, ob ein Code eine bestimmte Exception wirft

### Beispiele für Assertions

Testen wir die `getFullName()`- und `isAdult()`-Methoden unserer `Person`-Klasse:

```java
@Test
public void testGetFullName() {
    // Arrange
    LocalDate birthDate = LocalDate.of(1990, 1, 1);
    Person person = new Person("John", "Doe", birthDate);
    
    // Act
    String fullName = person.getFullName();
    
    // Assert
    assertEquals("John Doe", fullName);
}

@Test
public void testIsAdult_WhenAdult() {
    // Arrange
    LocalDate adultBirthDate = LocalDate.now().minusYears(25);
    Person person = new Person("John", "Doe", adultBirthDate);
    
    // Act & Assert
    assertTrue(person.isAdult());
}

@Test
public void testIsAdult_WhenNotAdult() {
    // Arrange
    LocalDate childBirthDate = LocalDate.now().minusYears(16);
    Person person = new Person("John", "Doe", childBirthDate);
    
    // Act & Assert
    assertFalse(person.isAdult());
}

@Test
public void testIsAdult_WhenExactlyEighteen() {
    // Arrange
    LocalDate eighteenBirthDate = LocalDate.now().minusYears(18);
    Person person = new Person("John", "Doe", eighteenBirthDate);
    
    // Act & Assert
    assertTrue(person.isAdult());
}
```

Diese Tests überprüfen:
1. Dass `getFullName()` den Vor- und Nachnamen korrekt zusammenfügt
2. Dass `isAdult()` für verschiedene Alterswerte die richtige Antwort zurückgibt

## 5. Ausnahmen testen

Eine wichtige Aufgabe von Tests ist es, sicherzustellen, dass Ihre Anwendung bei ungültigen Eingaben angemessen reagiert, typischerweise durch das Auslösen von Ausnahmen.

### Test für ungültige Geburtsdaten

Unsere `Person`-Klasse sollte eine `IllegalArgumentException` werfen, wenn wir versuchen, ein ungültiges Geburtsdatum zu setzen. Lassen Sie uns das testen:

```java
@Test
public void testSetFutureBirthDate_ThrowsException() {
    // Arrange
    Person person = new Person();
    LocalDate futureBirthDate = LocalDate.now().plusDays(1);
    
    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        person.setBirthDate(futureBirthDate);
    });
    
    String expectedMessage = "Birth date must be in the past";
    String actualMessage = exception.getMessage();
    
    assertTrue(actualMessage.contains(expectedMessage));
}

@Test
public void testConstructorWithFutureBirthDate_ThrowsException() {
    // Arrange
    LocalDate futureBirthDate = LocalDate.now().plusDays(1);
    
    // Act & Assert
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        new Person("John", "Doe", futureBirthDate);
    });
    
    String expectedMessage = "Birth date must be in the past";
    String actualMessage = exception.getMessage();
    
    assertTrue(actualMessage.contains(expectedMessage));
}
```

In diesen Tests:
1. Verwenden wir `assertThrows`, um zu überprüfen, dass der richtige Ausnahmetyp geworfen wird
2. Fangen wir die geworfene Ausnahme ein und überprüfen ihre Nachricht
3. Testen sowohl den direkten Aufruf von `setBirthDate()` als auch den parametrisierten Konstruktor

## 6. Test-Lebenszyklus mit Annotations

JUnit 5 bietet Annotations, um verschiedene Phasen des Test-Lebenszyklus zu steuern. Diese sind nützlich, um Code für die Test-Einrichtung und -Bereinigung zu organisieren.

### Lebenszyklus-Annotations

- `@BeforeAll`: Methode wird einmal vor allen Tests in der Klasse ausgeführt
- `@AfterAll`: Methode wird einmal nach allen Tests in der Klasse ausgeführt
- `@BeforeEach`: Methode wird vor jedem Test ausgeführt
- `@AfterEach`: Methode wird nach jedem Test ausgeführt

Hier ist ein Beispiel, wie wir diese Annotations in unseren Tests verwenden können:

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PersonLifecycleTest {

    private Person person;
    private static int testCount = 0;
    private static final LocalDate BIRTH_DATE = LocalDate.of(1990, 1, 1);

    @BeforeAll
    public static void setupAll() {
        System.out.println("==== Starting Person Tests ====");
        testCount = 0;
    }

    @BeforeEach
    public void setup() {
        person = new Person("John", "Doe", BIRTH_DATE);
        testCount++;
        System.out.println("Running test #" + testCount);
    }

    @Test
    public void testFullName() {
        assertEquals("John Doe", person.getFullName());
    }

    @Test
    public void testIsAdult() {
        assertTrue(person.isAdult());
    }

    @Test
    public void testEquals() {
        Person samePerson = new Person("John", "Doe", BIRTH_DATE);
        Person differentPerson = new Person("Jane", "Doe", BIRTH_DATE);
        
        assertEquals(person, samePerson);
        assertNotEquals(person, differentPerson);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Test #" + testCount + " completed");
        person = null;
    }

    @AfterAll
    public static void tearDownAll() {
        System.out.println("==== All Person Tests Completed ====");
        System.out.println("Total tests run: " + testCount);
    }
}
```

Mit diesen Annotations:
1. Initialisieren wir vor jedem Test ein neues `Person`-Objekt
2. Zählen wir, wie viele Tests ausgeführt wurden
3. Geben Informationen zum Testfortschritt aus
4. Setzen nach jedem Test das Person-Objekt auf `null` (zur Demonstration)

## 7. Parametrisierte Tests

Eine der mächtigsten Funktionen von JUnit 5 sind parametrisierte Tests, die uns erlauben, denselben Test mit verschiedenen Eingabewerten durchzuführen.

### Grundlegende parametrisierte Tests

JUnit 5 bietet verschiedene Möglichkeiten, Parameter für Tests anzugeben:

- `@ValueSource`: Für einfache Werte
- `@CsvSource`: Für mehrere Parameter pro Test
- `@MethodSource`: Für komplexere Testfälle, die durch eine Methode generiert werden

Hier ein Beispiel:

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class PersonParameterizedTest {

    @ParameterizedTest
    @ValueSource(ints = {18, 21, 30, 65, 100})
    public void testIsAdult_WhenAdult(int yearsAgo) {
        // Arrange
        LocalDate birthDate = LocalDate.now().minusYears(yearsAgo);
        Person person = new Person("Test", "Person", birthDate);
        
        // Act & Assert
        assertTrue(person.isAdult());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 10, 15, 17})
    public void testIsAdult_WhenNotAdult(int yearsAgo) {
        // Arrange
        LocalDate birthDate = LocalDate.now().minusYears(yearsAgo);
        Person person = new Person("Test", "Person", birthDate);
        
        // Act & Assert
        assertFalse(person.isAdult());
    }
}
```

### Erweiterte parametrisierte Tests

```java
@ParameterizedTest
@CsvSource({
    "John,Doe,John Doe",
    "Alice,Smith,Alice Smith",
    "Bob,Johnson,Bob Johnson",
    ",Smith, Smith",
    "John,,John "
})
public void testFullName(String firstName, String lastName, String expectedFullName) {
    // Arrange
    LocalDate birthDate = LocalDate.of(1990, 1, 1);
    Person person = new Person("", "", birthDate);
    
    // Act
    if (firstName != null) person.setFirstName(firstName);
    if (lastName != null) person.setLastName(lastName);
    
    // Assert
    assertEquals(expectedFullName, person.getFullName());
}

@ParameterizedTest
@MethodSource("createTestPersonsData")
public void testPersonCombinations(String firstName, String lastName, LocalDate birthDate, boolean shouldBeAdult) {
    // Arrange
    Person person = new Person(firstName, lastName, birthDate);
    
    // Assert
    assertEquals(firstName, person.getFirstName());
    assertEquals(lastName, person.getLastName());
    assertEquals(birthDate, person.getBirthDate());
    assertEquals(shouldBeAdult, person.isAdult());
}

// Method source for the parameterized test
private static Stream<Arguments> createTestPersonsData() {
    return Stream.of(
        Arguments.of("John", "Doe", LocalDate.now().minusYears(25), true),
        Arguments.of("Jane", "Smith", LocalDate.now().minusYears(17), false),
        Arguments.of("Bob", "Johnson", LocalDate.now().minusYears(18), true),
        Arguments.of("Alice", "Brown", LocalDate.now().minusYears(16), false)
    );
}
```

Mit diesen parametrisierten Tests:
1. Testen wir die `isAdult()`-Methode mit verschiedenen Altersangaben
2. Testen wir die `getFullName()`-Methode mit verschiedenen Namen-Kombinationen
3. Kombinieren wir mehrere Assertions in einem einzigen Test mit verschiedenen Testdaten

## 8. Mocking mit Mockito

Bei komplexeren Anwendungen müssen wir oft Klassen testen, die von anderen Komponenten abhängen. Mockito hilft uns dabei, diese Abhängigkeiten zu simulieren.

### Einführung eines Service und Repository

Unser Projekt enthält bereits eine Repository-Schnittstelle und einen Service:

```java
public interface PersonRepository {
    Person findByFullName(String fullName);
    void save(Person person);
}

public class PersonService {
    private final PersonRepository repository;
    
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }
    
    public Person findByFullName(String fullName) {
        return repository.findByFullName(fullName);
    }
    
    public void savePerson(Person person) {
        if (person == null) {
            throw new IllegalArgumentException("Person cannot be null");
        }
        
        repository.save(person);
    }
    
    public boolean isPersonEligibleForDiscount(String fullName) {
        Person person = repository.findByFullName(fullName);
        if (person == null) {
            return false;
        }
        
        // People under 18 or over 65 get a discount
        return person.getAge() < 18 || person.getAge() >= 65;
    }
}
```

### Testen mit Mockito

Nun können wir Mockito verwenden, um `PersonRepository` zu mocken und `PersonService` zu testen:

```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Person expectedPerson = new Person("John", "Doe", birthDate);
        when(repository.findByFullName("John Doe")).thenReturn(expectedPerson);
        
        // Act
        Person result = service.findByFullName("John Doe");
        
        // Assert
        assertEquals(expectedPerson, result);
        verify(repository).findByFullName("John Doe");
    }
}
```

### Weitere Tests für den Service

```java
@Test
public void testSavePerson() {
    // Arrange
    LocalDate birthDate = LocalDate.of(1990, 1, 1);
    Person person = new Person("John", "Doe", birthDate);
    
    // Act
    service.savePerson(person);
    
    // Assert
    verify(repository).save(person);
}

@Test
public void testSaveNullPerson_ThrowsException() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> {
        service.savePerson(null);
    });
    
    // Verify repository was never called
    verify(repository, never()).save(any());
}

@Test
public void testIsPersonEligibleForDiscount_Young() {
    // Arrange - person under 18
    LocalDate youngBirthDate = LocalDate.now().minusYears(15);
    Person youngPerson = new Person("Young", "Person", youngBirthDate);
    when(repository.findByFullName("Young Person")).thenReturn(youngPerson);
    
    // Act
    boolean result = service.isPersonEligibleForDiscount("Young Person");
    
    // Assert
    assertTrue(result);
}

@Test
public void testIsPersonEligibleForDiscount_Adult() {
    // Arrange - adult person (not eligible)
    LocalDate adultBirthDate = LocalDate.now().minusYears(30);
    Person adultPerson = new Person("Adult", "Person", adultBirthDate);
    when(repository.findByFullName("Adult Person")).thenReturn(adultPerson);
    
    // Act
    boolean result = service.isPersonEligibleForDiscount("Adult Person");
    
    // Assert
    assertFalse(result);
}

@Test
public void testIsPersonEligibleForDiscount_Senior() {
    // Arrange - senior person (eligible)
    LocalDate seniorBirthDate = LocalDate.now().minusYears(70);
    Person seniorPerson = new Person("Senior", "Person", seniorBirthDate);
    when(repository.findByFullName("Senior Person")).thenReturn(seniorPerson);
    
    // Act
    boolean result = service.isPersonEligibleForDiscount("Senior Person");
    
    // Assert
    assertTrue(result);
}
```

Mit Mockito können wir:
1. Ein Mock-Objekt für das Repository erstellen
2. Das Verhalten des Mocks definieren (was es zurückgeben soll)
3. Überprüfen, ob und wie oft Methoden des Mocks aufgerufen wurden

## 9. Testsuite organisieren

Bei größeren Projekten ist es sinnvoll, Tests in Suites zu organisieren. JUnit 5 bietet dafür eine einfache Möglichkeit:

```java
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    PersonTest.class,
    PersonLifecycleTest.class,
    PersonParameterizedTest.class,
    PersonServiceTest.class
})
public class PersonTestSuite {
    // This class remains empty - it's just used as a holder for the @Suite annotation
}
```

Diese Suite kombiniert alle unsere Test-Klassen und ermöglicht es, sie zusammen auszuführen.

## 10. Best Practices für das Testen

Abschließend einige Best Practices für JUnit 5-Tests:

### Namenskonventionen

- Verwenden Sie aussagekräftige Namen für Testmethoden, die beschreiben, was getestet wird
- Folgen Sie einem Muster wie: `testMethodName_Scenario_ExpectedBehavior`
- Beispiel: `testIsAdult_WhenAgeIsBelow18_ReturnsFalse`
- Nutzen Sie `@DisplayName` für noch klarere Testbeschreibungen

### Test-Organisation

- Ein Test sollte idealerweise nur einen Aspekt des Verhaltens testen
- Gruppieren Sie verwandte Tests in einer Testklasse
- Verwenden Sie Test-Suites für eine bessere Organisation
- Verwenden Sie das Arrange-Act-Assert-Muster mit Kommentaren zur besseren Lesbarkeit

### Testmethodik

- Folgen Sie dem Arrange-Act-Assert-Muster:
    - **Arrange**: Test-Objekte und -Bedingungen einrichten
    - **Act**: Die zu testende Methode aufrufen
    - **Assert**: Überprüfen, ob das Ergebnis den Erwartungen entspricht
- Testen Sie Randfälle und extreme Werte
- Testen Sie sowohl den glücklichen Pfad als auch Fehlerfälle
- Verwenden Sie statische Imports für Assertions (`import static org.junit.jupiter.api.Assertions.*`)

### Wartbarkeit

- Halten Sie Tests einfach und lesbar
- Vermeiden Sie komplexe Logik in Tests
- Verwenden Sie Hilfsmethoden für sich wiederholende Einrichtungscode
- Fügen Sie aussagekräftige Fehlermeldungen zu Assertions hinzu
- Nutzen Sie Lombok-Annotationen wie `@Data` zur Reduktion von Boilerplate-Code

## Zusammenfassung

JUnit 5 bietet ein robustes Framework für das Testen von Java-Anwendungen. Mit den in diesem Tutorial vorgestellten Techniken können Sie:

- Grundlegende Tests für Klassen schreiben
- Verschiedene Assertions verwenden, um Ihr erwartetes Verhalten zu überprüfen
- Ausnahmen testen
- Den Test-Lebenszyklus mit Annotations steuern
- Parametrisierte Tests für eine bessere Testabdeckung verwenden
- Abhängigkeiten mit Mockito mocken
- Tests in Suites organisieren

Indem Sie diese Praktiken in Ihren eigenen Projekten anwenden, können Sie die Qualität und Zuverlässigkeit Ihres Codes verbessern.