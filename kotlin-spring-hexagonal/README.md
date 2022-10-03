# Kotlin spring hexagonal

Boilerplate project for Kotlin with Spring Boot framework implementing hexagonal architecture.

## Technologies
* Kotlin
* Spring Boot framework
  * Web
  * Data JPA
* Spring Cloud framework
  * OpenFeign
* Zalando Problem Specification
* PostgreSQL
* Liquibase
* Redis
* DynamoDb
* Logback
* Groovy with Spock
* Test containers
* Localstack
* Ktlint

## Commands

### Unit tests
```
./gradlew test
```

### Integration tests
In case of running test containers, docker is required to be running
```
./gradlew integrationTest
```

### Architecture tests
```
./gradlew architectureTest
```

### All tests
In case of running test containers, docker is required to be running
```
./gradlew testAll
```

### Groovy linter
```
./gradlew codenarcTest
./gradlew codenarcIntegration
./gradlew codenarcArchitecture
```

or just
```
./gradlew codenarcAll
```

### Kotlin linter
```
./gradlew ktlintCheck
```

### Ktlint format
```
./gradlew ktlintFormat
```
