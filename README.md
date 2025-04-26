
# MiNegocio Service - Spring Boot 3.4.4

## Descripción

Este proyecto implementa un microservicio para la gestión de clientes y sus direcciones (**Address**), siguiendo las mejores prácticas de desarrollo en **Spring Boot** y utilizando **UUID** para mayor seguridad en los identificadores de entidades.

Se cubren operaciones **CRUD** básicas, listado por atributos específicos y manejo correcto de status de direcciones (matriz y sucursales).

---

## Tecnologías utilizadas

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **Docker Compose** (para base de datos)
- **Lombok**
- **Mockito + JUnit 5** (para pruebas unitarias)
- **ModelMapper** (para transformación de DTOs)

---

## Requisitos Previos

- Java 21 instalado
- Maven instalado
- Docker instalado y ejecutándose

---

## Configuración de Base de Datos

El proyecto utiliza un **docker-compose.yml** para levantar una instancia de PostgreSQL fácilmente.

1. Navega al directorio raíz del proyecto.
2. Ejecuta:

```bash
docker-compose up -d
```

Esto levantará una base de datos PostgreSQL en el puerto `5432`.

**Credenciales por defecto:**
- **Usuario**: postgres
- **Contraseña**: postgres
- **Base de datos**: minegocio_db

**Importante:** El `application.yml` ya está configurado para apuntar a este contenedor.

---

## Cómo ejecutar el proyecto

```bash
mvn clean install
mvn spring-boot:run
```

El proyecto correrá en:

http://localhost:8080/


---

## Endpoints principales

| Método | Endpoint                                           | Descripción                                                                    |
|:------|:---------------------------------------------------|:-------------------------------------------------------------------------------|
| POST  | `/customers`                                       | _**Crea un nuevo cliente con dirección matriz obligatoria**_                         |
| GET   | `/customers/{id}`                                  | _**Busca un cliente por ID y lista sus direcciones (matriz y sucursales)**_          |
| GET   | `/customers`                                       | _**Lista todos los clientes**_                                                       |
| GET   | `/customers/name/{name}`                           | _**Busca cliente por nombre**_                                                       |
| GET   | `/customers/identification/{identificationNumber}` | _**Busca cliente por número de identificación**_                                     |
| PUT   | `/customers/{id}`                                  | _**Actualiza información de cliente**_                                               |
| DELETE| `/customers/{id}`                                  | _**Borra un cliente (Soft delete para Address si tiene direcciones asociadas)**_     |
| **POST**  | `/address`                                         | _**_Crea una nueva direccion a un cliente escogido y evalua si es matriz o sucursal_**_ |
| GET   | `/address/{id}`                                            | Busca una direccion por ID y lista                                             |
| GET   | `/address`                                       | Lista todos las direcciones                                                    |
| PUT   | `/address/{id}`                                  | Actualiza información de la direccion                                          |
| DELETE| `/address/{id}`                                  | Borra una direccion (Soft delete)   |

---

## Ejemplos de Respuesta

**POST /customers** (Crear nuevo cliente colocando tambien la direccion matriz)

## mainAddress = matriz del customer (true or false)

```json
{
  "idCustomer": "2ecd153a-69ec-436d-8f0b-16f9d0ba7cb4",
  "identificationType": "RUC",
  "identificationNumber": "1720723677001",
  "name": "Eliot",
  "lastName": "Morocho",
  "email": "prueba2@gmail.com",
  "phoneNumber": "0988394473",
  "status": 1,
  "address": [
    {
      "idAddress": "e5fd5ed1-5a3b-497c-8184-fa4cea1ab466",
      "province": "Chimborazo",
      "city": "Riobamba",
      "address": "Lo nolibos y duran",
      "mainAddress": true,
      "status": 1
    }
  ]
}
```

**GET /customers/{id}** (Listar cliente y sus direcciones)

```json
{
  "idCustomer": "baeb0890-97c7-4b8b-b1a6-84565351f2b5",
  "identificationType": "RUC",
  "identificationNumber": "1720723640001",
  "name": "Edison Wladimir",
  "lastName": "Morocho Guayaquil",
  "email": "tidsec_inf@tidsec.com",
  "phoneNumber": "0993528317",
  "status": 1,
  "address": [
    {
      "idAddress": "d27448ee-2775-4f41-9374-dc6e94095663",
      "province": "Pichincha",
      "city": "Quito",
      "address": "Av. Amazonas y Colón",
      "mainAddress": true,
      "status": 1
    },
    {
      "idAddress": "2a8c9d4e-e2c0-422c-8dcb-df14c1f0d6a8",
      "province": "Azuay",
      "city": "Cuenca",
      "address": "teleferico y asoma",
      "mainAddress": false,
      "status": 1
    }
  ]
}
```
**POST /address** (Crea una nueva direccion y evalua si es matriz o no)

```json
{
  "customer":{
    "idCustomer": "df424197-03ab-4b0c-80ac-56389a828517"
  },
  "province": "Azuay",
  "city": "Cuenca",
  "address": "teleferico y asoma",
  "mainAddress": false,
  "status": 1
}
````

---

## Pruebas Unitarias

✅ Se cubren los principales endpoints del controlador:

- Crear cliente
- Actualizar cliente
- Eliminar cliente (soft delete direcciones)
- Buscar cliente por nombre
- Buscar cliente por número de identificación
- Buscar cliente por ID
- Listar todos los clientes

Las pruebas se encuentran en:
```text
src/test/java/com/tidsec/minegocio_service/controllers/CustomerControllerTest.java
```

---

## Colección de Postman

Se ha preparado una colección lista para importar y probar fácilmente todos los endpoints.

👉 Archivo: `MiNegocio-Service-Postman-Collection.json`

**Cómo importar en Postman:**

1. Abrir Postman.
2. Ir a **Importar**.
3. Seleccionar el archivo `.json`.
4. Probar los endpoints.

---
