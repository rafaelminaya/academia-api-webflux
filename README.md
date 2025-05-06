# 📦 API REST con Spring Boot 3.4.5 y WebFlux

Este proyecto es una API REST desarrollada con **Spring Boot 3.4.5** y **Java 21**, haciendo uso de **Spring WebFlux** para programación reactiva. Está configurado para ejecutarse en el puerto **8081** y se conecta a una base de datos **MongoDB** en el puerto **27020** a través de un archivo `docker-compose.yml`.  Y se han definido usuarios con roles para controlar el acceso a las rutas protegidas en la clase `WebSecurityConfig`.

---

## 🛠 Tecnologías

- Java 21
- Spring Boot 3.4.5
- Spring WebFlux
- Spring Security
- MongoDB 6.0.6
- Docker & Docker Compose

---

## 🚀 Ejecución

### 1. Clonar el repositorio 
```
git clone https://github.com/rafaelminaya/academia-api-webflux.git
```
### 2. Levantar MongoDB con Docker
```
docker-compose up -d
```
### 3. Ejecutar la aplicación Spring Boot 
```
./mvnw spring-boot:run
```
---

## 🔐 Usuarios disponibles

| Usuario  | Contraseña | Rol   |
|----------|------------|-------|
| mitocode | 123        | ADMIN |
| code     | 123        | USER  |


