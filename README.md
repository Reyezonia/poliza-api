# API de Gestión de Pólizas

API REST desarrollada con Spring Boot para la gestión de pólizas y sus riesgos asociados, incluyendo reglas de negocio para renovaciones, cancelaciones y validación de tipos de póliza.

## Tecnologías y Arquitectura

- **Java 17+** (Probado con Java 25)
- **Spring Boot 4.1.2**
- **Spring Data JPA / Hibernate**
- **Base de Datos:** H2 en memoria
- **Lombok** (para reducción de código repetitivo)
- **Arquitectura en Capas:** `Controller` → `Service` → `Repository`

## Características Principales

- **Seguridad:** Filtro interceptor que valida el header `x-api-key` en todas las rutas.
- **Mock Core:** Integración simulada que registra en los logs la intención de enviar eventos al Core (al renovar o cancelar).
- **Manejo Global de Excepciones:** Respuestas estandarizadas (404 para recursos no encontrados, 409 para conflictos de estado/reglas de negocio).
- **Reglas de Negocio:**
  - Renovación de pólizas aplicando un incremento por IPC (+10%).
  - Restricción de agregar riesgos únicamente a pólizas de tipo `COLECTIVA`.
  - Manejo estricto de estados de póliza: `ACTIVA`, `RENOVADA`, `CANCELADA`.

## Cómo ejecutar el proyecto

1. Clona el repositorio o descarga el código fuente.
2. Abre una terminal en la raíz del proyecto (donde se encuentra el archivo `pom.xml`).
3. Ejecuta el siguiente comando:

```bash
# En Windows (PowerShell)
.\mvnw.cmd spring-boot:run

# En Linux/Mac
./mvnw spring-boot:run
```
- La aplicación se iniciará en http://localhost:8081

## Consola H2 y Datos de Prueba Manuales 

- La aplicación cuenta con una base de datos H2 en memoria, la cual se inicializa vacia cada vez que el proyecto arranca. 

- para insertar o revisar datos manualmente, puedes acceder a la consola web de H2 en:
`http://localhost:8081/h2-console`

- **JDBC URL:** `jdbc:h2:mem:polizasdb`
- **User Name:** `sa`
- **Password:** *(dejar en blanco)*

**Script SQL de ejemplo:**
```sql
-- Insertar una póliza Individual en estado ACTIVA
INSERT INTO polizas (tipo, estado, canon, prima) 
VALUES ('INDIVIDUAL', 'ACTIVA', 1000.00, 500.00);

-- Insertar una póliza Colectiva en estado ACTIVA
INSERT INTO polizas (tipo, estado, canon, prima) 
VALUES ('COLECTIVA', 'ACTIVA', 5000.00, 2500.00);
```
## Documentacion endpoints Disponibles

- Para visualizar los endpoints disponibles y probarlos, puedes abrir la colección de Postman en el siguiente enlace:

<a href="https://sonia-5157540.postman.co/workspace/sonia's-Workspace~e085bed8-6bb1-4d5e-8480-b188a678276a/collection/43538111-376fbe23-f628-40ba-8fba-2057bfb4d339?action=share&creator=43538111" target="_blank">Ver Colección en Postman</a>
