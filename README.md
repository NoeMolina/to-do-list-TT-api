# To-Do List API — Backend

API REST para gestión de tareas y subtareas con autenticación JWT y control de roles, construida con arquitectura hexagonal (Ports & Adapters).

## Stack

- **Java 26** (Temurin)
- **Spring Boot 4.1.0**
- **Spring Data JPA** / Hibernate
- **PostgreSQL 17**
- **Flyway** — migraciones y seeders de base de datos
- **Spring Security + JWT** (jjwt)
- **Maven**

## Arquitectura

El proyecto sigue arquitectura hexagonal:

```
domain/           → Modelos de negocio puros, puertos (in/out), excepciones. Sin dependencias de framework.
application/      → Servicios que implementan los casos de uso (puertos de entrada).
infrastructure/   → Adaptadores: controllers REST, JPA entities/repositories, seguridad JWT, configuración.
```

## Requisitos previos

- Java 26
- Maven
- PostgreSQL corriendo (recomendado vía Docker)

## Levantar la base de datos (Docker)

```bash
docker compose up -d
```

Esto levanta Postgres con la base `todolist` en el puerto `5432`.

## Configuración

Variables relevantes en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/todolist
spring.datasource.username=postgres
spring.datasource.password=<tu_password>
jwt.secret=<clave_larga_y_aleatoria>
jwt.expiration-ms=86400000
```

## Ejecutar el proyecto

```bash
mvn clean spring-boot:run
```

Al levantar, Flyway aplica automáticamente las migraciones (`src/main/resources/db/migration/`), incluyendo los catálogos sembrados (`estatus`, `categorias`, `roles`) y el usuario administrador inicial.

La API queda disponible en `http://localhost:8080`.

## Bootstrap: primer usuario admin

El único usuario con rol `ADMIN` se crea vía seed de Flyway (`V10__seed_admin_user.sql`). El registro público (`POST /auth/register`) solo asigna rol `USER` y requiere estar autenticado como `ADMIN`.

## Roles y permisos

| Rol | Permisos |
|---|---|
| **ADMIN** | Ve y gestiona todas las tareas del sistema, puede asignar tareas a cualquier usuario, puede crear nuevos usuarios |
| **USER** | Ve y gestiona únicamente sus propias tareas |

## Ciclo de vida de tareas y subtareas

```
PENDIENTE ──> EN_PROGRESO ──> COMPLETADO
    └───────> CANCELADO
```

- No se puede completar una tarea si tiene subtareas sin finalizar (`PENDIENTE`/`EN_PROGRESO`).
- Al cancelar una tarea, sus subtareas no finalizadas se cancelan en cascada automáticamente.

## Endpoints principales

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| POST | `/auth/login` | Iniciar sesión, retorna JWT | Público |
| POST | `/auth/register` | Crear usuario nuevo | Solo ADMIN |
| GET | `/tasks` | Listar tareas (propias o todas si es admin) | Autenticado |
| POST | `/tasks` | Crear tarea | Autenticado |
| PATCH | `/tasks/{id}/status` | Cambiar estatus de tarea | Autenticado |
| POST | `/subtasks` | Crear subtarea | Autenticado |
| GET | `/subtasks/task/{taskId}` | Listar subtareas de una tarea | Autenticado |
| PATCH | `/subtasks/{id}/status` | Cambiar estatus de subtarea | Autenticado |
| GET | `/categories` | Listar catálogo de categorías | Autenticado |
| GET | `/statuses` | Listar catálogo de estatus | Autenticado |
| GET | `/users` | Listar usuarios | Solo ADMIN |

## Manejo de errores

Respuestas de error estructuradas y centralizadas (`@RestControllerAdvice`), con códigos HTTP semánticos:

- `400` — validación de datos / argumentos inválidos
- `401` — credenciales inválidas
- `404` — recurso no encontrado
- `422` — regla de negocio violada (ej. transición de estatus inválida, subtareas pendientes)

```json
{
  "timestamp": "2026-07-26T10:00:00",
  "status": 422,
  "error": "Unprocessable Entity",
  "message": "No se puede cambiar de 'COMPLETADO' a 'PENDIENTE'",
  "details": null
}
```
