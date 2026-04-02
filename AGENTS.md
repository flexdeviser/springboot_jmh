# Agent Instructions for springboot_jmh

## Project Overview
Spring Boot application with JMH (Java Microbenchmark Harness) for performance testing. Uses Java 17, Spring Boot 4.0.0, and Maven wrapper.

## Build Commands
```bash
# Build the project
./mvnw clean install

# Run tests
./mvnw test

# Run a single test
./mvnw test -Dtest=BenchmarkApplicationTests#runner

# Run Spring Boot application
./mvnw spring-boot:run
```

## Testing & Benchmarking
The primary test class `BenchmarkApplicationTests` contains both standard JUnit tests and JMH benchmarks. Run benchmarks using:
```bash
./mvnw test -Dtest=BenchmarkApplicationTests#runner
```

## Code Style Guidelines

### Formatting
- Use 4-space indentation (not tabs)
- Align opening braces at same column as code
- Add blank line between methods
- Add blank line at end of file

### Imports
- Group imports: third-party → Spring → project
- Import static classes only if used multiple times
- Remove unused imports after refactoring

### Naming Conventions
- **Classes**: PascalCase (e.g., `BenchmarkApplication`, `Greeting`)
- **Methods**: camelCase (e.g., `sayHello`, `setDslContext`)
- **Fields**: camelCase (e.g., `greeting`, `name`)
- **Constants**: UPPER_SNAKE_CASE
- **Package**: lowercase, avoid underscores (e.g., `org.e4s.benchmark`)

### Java Version
- Target Java 17
- Use `final` for method parameters when appropriate (e.g., `final String name`)

### Spring Boot
- Annotate services with `@Service`
- Inject dependencies using constructor or setter injection
- Use `@Autowired` for setter injection when needed

### JMH Annotations
- Use `@State(Scope.Benchmark)` for shared state
- Use `@Benchmark` to mark benchmark methods
- Use `@Setup` and `@TearDown` for initialization/cleanup
- Specify `@BenchmarkMode` and `@OutputTimeUnit` at class level

### Error Handling
- Avoid checked exceptions in business logic
- Return null or empty collections instead of throwing
- Use simple logging with Spring's logger

## Repository Rules
- Do not modify generated JMH files in `target/generated-test-sources/`
- Keep main code in `src/main/java/`
- Keep test code in `src/test/java/`
- No pre-commit hooks configured - run tests manually before committing