# 🏯 Bushido Challenge - API de Gestión

## ✨ Funcionalidades Implementadas

### 🔄 Operaciones CRUD
- Gestión con borrado lógico de:
  - 👥 Usuarios
  - 🏷️ Grupos de Usuarios
  - ⚙️ Configuraciones de Grupo

### 🔗 Relaciones Avanzadas
- Many-to-Many (`Usuario` ↔ `Grupo`)
- One-to-Many (`Grupo` → `Configuracion`)

### 🎯 Funcionalidades Clave
- Asignación/desasignación dinámica de grupos
- Modificación de niveles de acceso
- 💱 Integración con API Frankfurter para cotizaciones
- 📊 Sistema de auditoría asíncrono
- 📚 Documentación OpenAPI (Swagger UI)
- 🛡️ Manejo centralizado de errores

## 🛠️ Stack Tecnológico

| Categoría         | Tecnologías                                                                 |
|-------------------|-----------------------------------------------------------------------------|
| **Core**          | Java 21 • Spring Boot 3.x.x                                                 |
| **Build**         | Gradle (Groovy DSL)                                                         |
| **Persistencia**  | Spring Data JPA • Hibernate • PostgreSQL                                    |
| **API**           | Spring Web MVC • WebFlux (WebClient)                                        |
| **Asincronía**    | `@EnableAsync` • `@Async`                                                   |
| **Documentación** | Springdoc OpenAPI                                                           |
| **Utilidades**    | Lombok • Jakarta Bean Validation                                            |

## ⚙️ Configuración Inicial

### 📦 Prerrequisitos
- **JDK 21+**
- **Gradle** (incluye wrapper)
- **PostgreSQL 12+** (o Docker)
- Cliente API (Postman/Insomnia) recomendado

### 🐘 Opciones de Base de Datos

#### 🔵 Docker (Recomendado)
```bash
docker run --name postgres-challenge \
  -e POSTGRES_USER=user \
  -e POSTGRES_PASSWORD=password \
  -e POSTGRES_DB=challenge_db \
  -p 5432:5432 -d postgres:latest
```

⚙️ Manual

CREATE USER user WITH PASSWORD 'password';  <br>
CREATE DATABASE challenge_db OWNER user;

🔧 Configuración de Aplicación

# application.properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:postgresql://localhost:5432/challenge_db}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:user}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:password}
spring.jpa.hibernate.ddl-auto=update
frankfurter.api.base-url=${FRANKFURTER_API_BASE_URL}

# 🚀 Ejecución

## 🔨 Construcción
./gradlew clean build

## ▶️ Iniciar Aplicación
### Opción 1 (desarrollo)
./gradlew bootRun

### Opción 2 (producción)
java -jar build/libs/technical-challenge-*.jar

🔍 Swagger UI disponible en: http://localhost:8080/swagger-ui.html

🌐 Endpoints Principales

Método	                  Ruta	                      Descripción  <br>
POST	                    /api/v1/users	              Crear usuario <br>
GET	                      /api/v1/users/{id}	        Obtener usuario por ID  <br>
PUT	                      /api/v1/users/{id}	        Actualizar usuario  <br>
DELETE	                  /api/v1/users/{id}	        Borrado lógico <br>
POST	                    /api/v1/users/{id}/groups	  Asignar grupos <br>
Ejemplo cURL: archivo Bushido.postman_collection.json

🏗️ Decisiones de Diseño  <br>

UUIDs para identificación distribuida  <br>
Borrado lógico con flags active <br>
Patrón DTO para desacoplamiento <br>
WebClient para llamadas no bloqueantes <br>
Auditoría asíncrona con tolerancia a fallos <br>
Manejo centralizado de errores
