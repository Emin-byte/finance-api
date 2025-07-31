# Finance API Test Assignment (Gradle)

## Description
Simple REST API for a financial product. Users have balances and can have transactions applied.

## Tech Stack
- Java 17
- Spring Boot 3.2
- PostgreSQL
- Gradle

## Endpoints
- `POST /api/users` : create user. Body: `{ "name": "Name", "initialBalance": 100.0 }`
- `PUT /api/users/{id}` : update user's name. Body: `{ "name": "New Name" }`
- `GET /api/users` : list users
- `GET /api/users/{id}` : get user
- `POST /api/transactions` : create transaction. Body: `{ "userId": "uuid", "amount": 100.0 }`
- `GET /api/transactions?from=<ISO>&to=<ISO>&userId=<uuid optional>` : list transactions filtered by date and optionally user

## Requirements
### Database
PostgreSQL must be running. Example connection (default):
- URL: `jdbc:postgresql://localhost:5432/finance`
- Username: `postgres`
- Password: `postgres`

Make sure extension for `gen_random_uuid()` is created:
```sql
CREATE EXTENSION IF NOT EXISTS pgcrypto;
```

### Build and run
```bash
./gradlew clean bootJar
java -jar build/libs/finance-api-0.1.0.jar
```

or directly
```bash
./gradlew bootRun
```

### Docker build
```bash
docker build -t finance-api .
docker run -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/finance -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=postgres -p 8080:8080 finance-api
```

## Notes
- `spring.jpa.hibernate.ddl-auto=update` для простоты; в продакшне лучше миграции.
- Входные DTO валидируются через аннотации.
