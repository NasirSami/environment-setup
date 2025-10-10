# Employee API Demo

Simple Spring Boot REST API showcasing CRUD endpoints with exception handling.

## Features
- `POST /api/employees` – add a new employee
- `GET /api/employees` – retrieve all employees
- `GET /api/employees/{id}` – retrieve employee by id
- `PUT /api/employees/{id}` – update employee details
- `DELETE /api/employees/{id}` – remove employee
- Centralized exception handling for not found and validation errors.
- In-memory data store backed by `ConcurrentHashMap` (no database required).

## Run
From this directory:

```bash
mvn spring-boot:run
```

Example `curl` commands:

```bash
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Ada","lastName":"Lovelace","email":"ada@example.com","role":"Engineer"}'

curl http://localhost:8080/api/employees
curl http://localhost:8080/api/employees/1
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Ada","lastName":"Lovelace","email":"ada@example.com","role":"Lead Engineer"}'
curl -X DELETE http://localhost:8080/api/employees/1
```

Validation errors and missing records return structured JSON with appropriate HTTP status codes.
