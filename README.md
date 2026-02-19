# Franchise Management API - Accenture Test

API reactiva desarrollada con **Java 17** y **Spring Boot 3** para la gestión integral de franquicias, sucursales y productos. El sistema utiliza **Amazon DynamoDB** como base de datos NoSQL bajo un modelo de documento único para optimizar el rendimiento.

## 🚀 Arquitectura
El proyecto implementa **Arquitectura Hexagonal (Ports & Adapters)**, asegurando que la lógica de negocio esté completamente aislada de la infraestructura.

* **Domain:** Contiene los modelos de negocio (`Franchise`, `Branch`, `Product`).
* **Application:** Define los puertos de entrada (`FranchiseUseCase`) y salida (`FranchiseRepositoryPort`), junto con el servicio que orquestra la lógica.
* **Infrastructure:** Implementa los adaptadores de entrada (REST Controllers con WebFlux) y salida (DynamoDB Enhanced Client).

## 🛠️ Tecnologías Utilizadas
* **Java 17**
* **Spring WebFlux** (Programación Reactiva)
* **AWS SDK for Java v2** (DynamoDB Enhanced Client)
* **Project Reactor** (Mono y Flux)
* **Lombok**
* **Maven**

## 💻 Configuración y Despliegue Local

### 1. Requisitos Previos
* **Java 17** instalado.
* Maven 3.8+ instalado.
* Cuenta de AWS con una tabla en DynamoDB llamada `Franchise` (Partition Key: `id` de tipo String).

### 2. Variables de Entorno
Configura tus credenciales de AWS en tu entorno local para que el SDK las detecte automáticamente:
```bash
export AWS_ACCESS_KEY_ID=tu_access_key
export AWS_SECRET_ACCESS_KEY=tu_secret_key
export AWS_REGION=us-east-1
```

### 3. Instalación y Ejecución
```bash
# Clonar el repositorio
git clone https://github.com/daniieel3846/franchise-management-api.git
cd franchise-management-api

# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

## 📋 Endpoints Principales

### Franquicias
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| `GET` | `/api/franchises` | Obtener todas las franquicias |
| `GET` | `/api/franchises/{id}` | Obtener una franquicia por ID |
| `POST` | `/api/franchises` | Crear una nueva franquicia |
| `PUT` | `/api/franchises/{id}` | Actualizar información de la franquicia |
| `DELETE` | `/api/franchises/{id}` | Eliminar una franquicia |
| `PUT` | `/api/franchises/{id}/name` | Actualizar nombre de la franquicia |
| `PUT` | `/api/franchises/{id}/branches/{branchId}/stock` | Actualizar stock de producto en sucursal |
| `GET` | `/api/franchises/{id}/branches/{branchId}/products/top-stock` | Obtener producto con mayor stock en una sucursal |

## 📦 Estructura del Proyecto
```
src/
├── main/
│   ├── java/com/accenture/challenge/franchise_management_api/
│   │   ├── domain/
│   │   │   └── model/
│   │   │       ├── Franchise.java
│   │   │       ├── Branch.java
│   │   │       └── Product.java
│   │   ├── application/
│   │   │   ├── dto/
│   │   │   │   ├── NameUpdateDTO.java
│   │   │   │   ├── StockUpdateDTO.java
│   │   │   │   └── TopStockProductResponse.java
│   │   │   ├── ports/
│   │   │   │   └── in/
│   │   │   │       └── FranchiseUseCase.java
│   │   │   └── service/
│   │   │       └── FranchiseService.java
│   │   ├── infrastructure/
│   │   │   ├── adapters/
│   │   │   │   ├── dto/
│   │   │   │   │   └── TopStockProductResponse.java
│   │   │   │   └── in/
│   │   │   │       └── FranchiseController.java
│   │   │   └── config/
│   │   │       └── DynamoDbConfig.java
│   │   └── FranchiseManagementApiApplication.java
│   └── resources/
│       └── application.yaml
└── test/
    └── java/com/accenture/challenge/franchise_management_api/
        └── FranchiseManagementApiApplicationTests.java
```

## 🧪 Pruebas Unitarias
```bash
# Ejecutar todas las pruebas
mvnw.cmd test


# Ejecutar pruebas de un módulo específico
mvnw.cmd test -Dtest=NombreDelTest

# Ejecutar pruebas con cobertura
mvn test jacoco:report
```

## 📝 Ejemplos de Uso

### Crear una Franquicia
```json
POST /api/franchises
{
  "name": "Mi Franquicia",
  "branches": [
    {
      "name": "Sucursal Centro",
      "address": "Calle Principal 123",
      "products": [
        {
          "name": "Producto A",
          "stock": 100
        }
      ]
    }
  ]
}
```

### Actualizar Stock
```json
PUT /api/franchises/{franchiseId}/branches/{branchId}/stock
{
  "productName": "Producto A",
  "newStock": 150
}
```



