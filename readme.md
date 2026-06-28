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

## Notes

- Flyway runs migrations automatically on startup
- Database is provided via Docker
- Application starts after MariaDB is healthy
