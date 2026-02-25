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

#despliega la tabla de DynamoDB automáticamente con este comando:
Ejecute el siguiente comando para desplegar la base de datos y los roles necesarios:

aws cloudformation deploy --template-file template.yaml --stack-name franchise-infra-stack --region us-east-2 --capabilities CAPABILITY_IAM
  
# Ejecutar la aplicación
mvn spring-boot:run
```

La API estará disponible en `http://http://api-accenture-env.eba-vdtmss4q.us-east-2.elasticbeanstalk.com:8080`.

## 📋 Endpoints Principales
## 🚀 API Endpoints

La API sigue los principios REST y utiliza JSON como formato de intercambio de datos. A continuación se detallan los endpoints disponibles:

### 🏢 Gestión de Franquicias y Sucursales
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/franchise` | Crear una nueva franquicia con sus sucursales y productos iniciales. |
| `PUT` | `/api/franchise/{id}/name` | Actualizar el nombre de una franquicia existente. |
| `POST` | `/api/franchise/{id}/branch` | Agregar una nueva sucursal a una franquicia específica. |
| `PUT` | `/api/franchise/{id}/branch/{branchName}/name` | Actualizar el nombre de una sucursal. |

### 📦 Gestión de Productos y Stock
| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| `POST` | `/api/franchise/{id}/branch/{branchName}/product` | Agregar un nuevo producto a una sucursal específica. |
| `DELETE` | `/api/franchise/{id}/branch/{branchName}/product/{productName}` | Eliminar un producto de una sucursal. |
| `PUT` | `/api/franchise/{id}/branch/{branchName}/product/{productName}/stock` | **Requerimiento:** Modificar el stock de un producto específico. |
| `GET` | `/api/franchise/{id}/max-stock` | **Requerimiento:** Obtener el producto con mayor stock por cada sucursal de una franquicia. |

---

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
│   │   │   │   
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


### 1. Crear una Franquicia (Inicial)
**POST** `/api/franchise`
```json
{
  "name": "Franquicia Accenture Bogota",
  "branches": []
}

### Crear una Franquicia con Sucursal y Producto Inicial
```json
POST /api/franchises
{
  "name": "Mi Franquicia",
  "branches": [
    {
      "name": "Sucursal Centro",
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
  "newStock": 150
}
```



