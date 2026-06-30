# Pismo Coding Assessment

REST API built with **Spring Boot 4**, using **MariaDB**, **Flyway**, and **Docker Compose**.

---

## Requirements

- Docker
- Docker Compose

---

## Configuration

Create a `.env` file in the project root:

```env
MARIADB_HOST=mariadb
MARIADB_PORT=3306
MARIADB_DATABASE=mydatabase
MARIADB_USER=user
MARIADB_PASSWORD=secret-password
MARIADB_ROOT_PASSWORD=very-secret-password
```

---

## Run the project

Start the application:

```bash
docker compose up
```

The API will be available at:

```
http://localhost:8080
```

---

## API Documentation

Swagger UI:

```
http://localhost:8080/swagger-ui/index.html#/
```

---

## Stop the project

```bash
docker compose down
```

Remove volumes:

```bash
docker compose down -v
```

---

## Tests

Run tests:

```bash
mvn test
```

Run application without tests:

```bash
mvn -DskipTests spring-boot:run
```

---

## Services

| Service | Description | Port |
|--------|-------------|------|
| mariadb | Database | 3306 |
| app | Spring Boot API | 8080 |

---

## Technologies Used

*   **Spring Boot 4**: Framework for building Java applications.
*   **MariaDB**: Relational database management system.
*   **Flyway**: Database versioning tool.
*   **Docker**: Platform for developing, shipping, and running applications in containers.
*   **Docker Compose**: Tool for defining and running multi-container Docker applications.
*   **Lombok**: Library to reduce boilerplate code in Java.
*   **Springdoc OpenAPI**: Automatic generation of OpenAPI documentation (Swagger UI).
*   **Commons Codec**: Library for encoding and decoding data (used for hashing).
*   **Jackson Databind**: Library for JSON serialization/deserialization (integrated via Spring Boot Web MVC).

---

## Suggested Next Steps

*   **Redis Cache Implementation**: Add a caching layer using Redis to improve API performance, especially for frequent queries.
*   **Transaction Idempotency**: Refine and expand the idempotency implementation to ensure that transaction creation operations are safe against duplicate retries.

---

## Notes

- Flyway runs migrations automatically on startup.
- The database is provided via Docker.
- The application starts after MariaDB is healthy.