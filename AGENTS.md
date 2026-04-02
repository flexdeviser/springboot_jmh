# Agent Instructions for springboot_jmh

## Project Overview
Spring Boot application with JMH (Java Microbenchmark Harness) for performance testing. Uses Java 17, Spring Boot 4.0.0, JMH 1.37, and Maven wrapper.

## Build Commands

### Standard Operations
```bash
# Build the entire project
./mvnw clean install

# Clean and rebuild
./mvnw clean install

# Run all tests
./mvnw test

# Run tests without build
./mvnw test -DskipTests=false

# Run a single test method
./mvnw test -Dtest=BenchmarkApplicationTests#runner

# Run tests with specific class
./mvnw test -Dtest=BenchmarkApplicationTests

# Run Spring Boot application
./mvnw spring-boot:run

# Package application
./mvnw clean package
```

### Code Quality
```bash
# Verify code style (if available)
./mvnw verify

# Check dependencies
./mvnw dependency:analyze
```

## Testing & Benchmarking

### Running Benchmarks
The primary test class `BenchmarkApplicationTests` contains both standard JUnit tests and JMH benchmarks.

**Run benchmarks:**
```bash
./mvnw test -Dtest=BenchmarkApplicationTests#runner
```

**Key benchmark configuration:**
- Warmup iterations: 2
- Warmup time: 10 seconds
- Measurement iterations: 3
- Forks: 0 (for debugging)
- Mode: Throughput
- Time unit: Microseconds

### JMH Best Practices
- Use `@State(Scope.Benchmark)` for shared state
- Mark benchmark methods with `@Benchmark`
- Use `@Setup(Level.Trial)` for initialization and `@TearDown(Level.Trial)` for cleanup
- Keep benchmark setup and teardown minimal
- Run with forks=0 for debugging to see actual execution
- Add comments explaining the purpose of benchmark setup

## Code Style Guidelines

### General Formatting
- Use 4-space indentation (not tabs)
- Align opening braces at same column as code
- Add blank line between methods
- Add blank line at end of file
- Maximum line length: 100-120 characters

### Import Organization
Group imports in the following order, each separated by a blank line:

1. **Third-party imports** (e.g., `org.openjdk.jmh`, `java.util`)
2. **Spring imports** (e.g., `org.springframework.boot`, `org.springframework.stereotype`)
3. **Project imports** (e.g., `org.e4s.benchmark`)
4. **Static imports** only if used multiple times
5. Remove unused imports after refactoring

Example:
```java
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.springframework.stereotype.Service;
import org.e4s.benchmark.service.Greeting;
import org.e4s.benchmark.service.Greeting;
```

### Naming Conventions

**Classes**: PascalCase
- Examples: `BenchmarkApplication`, `Greeting`, `ServiceConfig`
- Services use `@Service` annotation
- Test classes can use `Tests` suffix or use the class being tested

**Methods**: camelCase
- Examples: `sayHello`, `setDslContext`, `setup`, `teardown`
- Private methods: camelCase
- Public methods: camelCase
- Override methods: follow the overridden method name

**Fields**: camelCase
- Examples: `greeting`, `name`, `dslContext`, `counter`
- Use `final` for immutable fields
- Use `final` for method parameters when appropriate

**Constants**: UPPER_SNAKE_CASE
- Examples: `MAX_RETRIES`, `DEFAULT_TIMEOUT`, `API_BASE_URL`
- Public constants should be documented

**Packages**: lowercase, no underscores
- Examples: `org.e4s.benchmark`, `org.e4s.benchmark.service`
- Group related classes together

### Type Annotations
- Use Java 17 features (records, sealed classes when appropriate)
- Prefer `final` for parameters in interfaces and public methods
- Use primitive types when appropriate
- Consider nullability annotations when needed

### Java Version Specifics
- Target Java 17
- Use `var` for local variables when type is clear
- Use text blocks for multi-line strings
- Use pattern matching for switch when applicable
- Prefer records for data transfer objects

### Spring Boot Patterns
- Annotate services with `@Service`
- Inject dependencies using constructor injection by default
- Use `@Autowired` for setter injection only when necessary
- Use `@Value` for simple configuration properties
- Keep configuration in `application.yml` or `application.properties`

### JMH Patterns
- Keep benchmark setup minimal and predictable
- Use `@State(Scope.Benchmark)` for shared state across iterations
- Specify `@BenchmarkMode` and `@OutputTimeUnit` at class level
- Document the purpose of each benchmark setup method
- Avoid modifying shared state in benchmarks

### Error Handling
- Avoid checked exceptions in business logic
- Return null or empty collections instead of throwing exceptions
- Use Spring's logger (`private static final Logger logger = LoggerFactory.getLogger(ClassName.class);`)
- Log errors with context using logger.error(), logger.warn(), logger.info()
- Use try-catch blocks for critical sections with appropriate logging
- Consider custom exceptions for domain-specific errors

### Documentation
- Add Javadoc for public classes and methods
- Include example usage in Javadoc comments
- Keep Javadoc concise and focused
- Use JavaDoc tags: `@param`, `@return`, `@throws` where applicable

### Test Organization
- Place test code in `src/test/java/` following same package structure
- Use meaningful test class names (e.g., `GreetingServiceTest`)
- Use descriptive test method names (e.g., `testSayHelloReturnsCorrectMessage`)
- Follow AAA pattern: Arrange, Act, Assert
- Isolate tests - each test should be independent

## Project Structure

### Main Source
```
src/main/java/
├── org/e4s/benchmark/
│   ├── BenchmarkApplication.java          # Main Spring Boot application
│   └── service/
│       └── Greeting.java                   # Business service
```

### Test Source
```
src/test/java/
├── org/e4s/benchmark/
│   └── BenchmarkApplicationTests.java     # Main test and benchmark class
```

### Configuration
- `pom.xml` contains all dependencies and build configuration
- Spring Boot auto-configuration uses default settings
- No additional configuration files needed for basic functionality

## Repository Rules

- Do NOT modify generated JMH files in `target/generated-test-sources/`
- Keep main code in `src/main/java/`
- Keep test code in `src/test/java/`
- Keep benchmark configuration in test classes, not generated files
- No pre-commit hooks configured - run tests manually before committing
- Generated documentation files go to `target/` directory

## Common Pitfalls

- **Benchmark state management**: Always use `@State(Scope.Benchmark)` and ensure Spring can inject into static fields
- **Thread safety**: JMH runs benchmarks in multiple threads, avoid shared mutable state
- **Warmup considerations**: JMH needs warmup iterations to stabilize results
- **Fork count**: Set forks=0 when debugging benchmarks to see actual execution
- **Timing**: Use appropriate time units based on benchmark characteristics