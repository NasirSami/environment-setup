# Spring Boot Actuator Demo

Assignment checklist completed in this module:

1. Created a Spring Boot application with the Web and Actuator starters (see `pom.xml`).
2. Added Spring Boot DevTools for auto-restart in development (`spring-boot-devtools` dependency).
3. Exposed and explored Actuator endpoints, including a custom `TrainingInfoContributor` (see `/actuator/info`, `/actuator/env`).
4. Managed configuration via `application.yml`, including custom `info.*` metadata.
5. Added a custom `@PropertySource` (`config/custom-port.properties`) plus `ServerPortCustomizer` to override the port at runtime.
6. Created REST endpoints under `/api` that demonstrate different response codes (`201 Created`, `202 Accepted`).
7. Copied the original `.properties` values into `application-properties-notes.txt` and rewrote them as YAML in `application.yml`.

## Run the app

```bash
mvn spring-boot:run
```

With the custom port override active, the app listens on `http://localhost:9095`. Useful endpoints:

- `GET /api/status` – health check returning JSON payload
- `POST /api/orders` – mock create endpoint returning `201 Created`
- `GET /api/orders/{id}` – returns `202 Accepted` to mimic async processing
- `GET /actuator` – actuator index (all endpoints exposed)
- `GET /actuator/health` – detailed health info
- `GET /actuator/info` – shows custom info contributor output
- `GET /actuator/env` – environment properties (enabled because all endpoints are exposed)

To revert to the port defined in `application.yml`, comment out or change `custom.server.port` in `config/custom-port.properties`.

DevTools auto restart is enabled by default when running with `spring-boot:run` or the IDE’s boot dashboard—any code/resource changes trigger a quick restart.
