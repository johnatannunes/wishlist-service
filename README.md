# Wishlist Service

## Overview

Welcome to the wish list service project! This service is designed to meet customers' wish lists. Customers can add,
view and remove products from their wish lists, providing a seamless shopping experience.
## Features

- **Create a Wishlist:** Customers can create a new wishlist.
- **View Wishlists:** Retrieve all wishlists for a given customer or get details of a specific wishlist.
- **Add Products:** Add products to a wishlist, with constraints on product quantity and uniqueness.
- **Remove Products:** Remove products from a wishlist.
- **Fetch product data: Retrieve product details from an external API and handle unavailable products.
- **Get Product in Wishlist:** Fetch specific product details within a wishlist.

## Architecture

This service is designed following Clean Architecture principles with the following structure:

- **Domain Layer:** Contains business models and domain services.
- **Application Layer:** Contains use cases for business operations.
- **Adapter Layer:** Implements the interfaces to external systems like web controllers and MongoDB persistence.
- **Configuration:** Application-wide configurations.

### Code Structure

```plaintext

com.appstack.wishlist
│
├── adapter
│   ├── cache
│   ├── external
│   │   └── dto
│   ├── mapper
│   └── web
│       ├── controller
│       └── dto
│
├── application
│   ├── service
│   └── usecase
│
├── common
│   └── logging
│
├── config
│
├── domain
│   ├── enums
│   ├── kafka
│   │       ├── consumer
│   │       ├── producer
│   ├── model
│   ├── repository
│   └── service
│
├── exception
│
├── infrastructure
│   └── filter
│
└── WishlistApplication

test
│
└── java
    └── com.appstack.wishlist
        └── cucumber
            ├── context
            ├── features
            └──stepDefs


```

### Technologies Used

##### This project uses the following technologies and tools:

- **Java 21**: Linguagem de programação principal do projeto.
- **Spring Boot 3**: Framework para desenvolvimento de aplicações Java.
    - **spring-boot-starter-data-mongodb**: Starter para integração com MongoDB.
    - **spring-boot-starter-web**: Starter para desenvolvimento de aplicações web.
    - **spring-boot-starter-validation**: Starter para validação de dados.
    - **spring-boot-starter-aop**: Starter para Aspect-Oriented Programming.
    - **spring-boot-starter-actuator**: Starter para endpoints de gerenciamento e monitoramento.
    - **spring-boot-starter-cache**: Starter para suporte a cache.
- **Lombok**: Biblioteca para reduzir o boilerplate de código Java.
- **MapStruct**: Biblioteca para mapeamento entre objetos Java.
- **Springdoc OpenAPI**: Ferramenta para geração automática de documentação OpenAPI.
- **SLF4J**: API de logging para Java.
- **Logback**: Implementação de logging para SLF4J.
- **Resilience4j**: Biblioteca para padrões de resiliência, como Circuit Breaker e Retry.
    - **resilience4j-spring-boot3**: Integração com Spring Boot 3 para Resilience4j.
    - **resilience4j-retry**: Módulo para implementar a estratégia de retry.
- **Caffeine**: Biblioteca para caching de alta performance.
- **Spring Kafka**: Suporte para integrar com o Kafka.
- **Cucumber**: Framework para testes BDD (Behavior-Driven Development).
    - **cucumber-java**: Dependência para integração com Java.
    - **cucumber-spring**: Integração com Spring para testes Cucumber.
    - **cucumber-junit**: Integração com JUnit para execução de testes Cucumber.
    - **cucumber-junit-platform-engine**: Suporte para a plataforma JUnit 5.
- **JUnit**: Framework para testes unitários.
    - **junit**: Biblioteca de testes unitários.
    - **junit-platform-suite**: Suporte para execução de suites de testes.
- **Flapdoodle Embedded MongoDB**: Biblioteca para iniciar uma instância embutida do MongoDB para testes.

### Concepts Covered

This project utilizes various technologies that cover the following concepts and practices:

### Concepts and Practices

#### 1. **Object-Oriented Programming (OOP)**

- **Java 21**: A programming language that supports OOP principles such as encapsulation, inheritance, and polymorphism.

#### 2. **Web Application Development**

- **Spring Boot 3**
    - **spring-boot-starter-web**: Creating RESTful APIs and developing web applications.

#### 3. **Data Persistence**

- **Spring Boot 3**
    - **spring-boot-starter-data-mongodb**: Integration with MongoDB for CRUD operations and querying NoSQL databases.

#### 4. **Data Validation**

- **Spring Boot 3**
    - **spring-boot-starter-validation**: Validating API request data using annotations like `@NotNull`, `@Size`, etc.

#### 5. **Functional and Aspect-Oriented Programming**

- **Spring Boot 3**
    - **spring-boot-starter-aop**: Implementing Aspect-Oriented Programming (AOP) for separating cross-cutting concerns like logging and security.

#### 6. **Monitoring and Management**

- **Spring Boot 3**
    - **spring-boot-starter-actuator**: Providing endpoints for application monitoring and management.

#### 7. **Caching**

- **Spring Boot 3**
    - **spring-boot-starter-cache**: Implementing caching strategies to improve performance and data efficiency.

- **Caffeine**: High-performance in-memory caching library.

#### 8. **API Documentation**

- **Springdoc OpenAPI**: Automatic OpenAPI documentation generation for RESTful APIs, with Swagger UI support.

#### 9. **Resilience and Fault Tolerance**

- **Resilience4j**
    - **resilience4j-spring-boot3**: Implementing resilience patterns like Circuit Breaker and Retry to handle external call failures.
    - **resilience4j-retry**: Retry strategy for operations that may fail intermittently.

#### 10. **Data Mapping**

- **MapStruct**: Data mapping between different representations, such as DTOs and entities.

#### 11. **Software Testing**

- **JUnit**
    - **junit**: Framework for unit and integration testing.
    - **junit-platform-suite**: Creating test suites for organizing and running tests.

- **Cucumber**
    - **cucumber-java**: BDD framework for writing behavior-driven tests.
    - **cucumber-spring**: Spring integration for Cucumber tests.
    - **cucumber-junit**: JUnit integration for running Cucumber tests.
    - **cucumber-junit-platform-engine**: Support for running Cucumber tests on the JUnit 5 platform.

- **Flapdoodle Embedded MongoDB**: Embedded MongoDB instance for testing without requiring a real database.

#### 12. **Logging**

- **SLF4J**
    - **slf4j-api**: Logging API for Java, providing an abstraction layer for various logging frameworks.

- **Logback**
    - **logback-classic**: SLF4J logging implementation with XML configuration support.

#### 13. **Agile Development and DevOps Practices**

- **Maven**: Build automation tools for compiling, testing, and packaging the application.

- **Docker and Docker Compose**: Containerization and multi-service configuration for development and testing.

# Technologies and Concepts Covered

This `docker-compose.yml` file sets up the following technologies and concepts:

### Technologies and Concepts

#### 1. **MongoDB**

- **Image:** `mongo:latest`
- **Concept:** NoSQL Database for storing and managing data.
- **Configuration:** Root username and password, initial database setup, and data persistence.
- **Ports:** `27017:27017`

#### 2. **MockServer**

- **Image:** `mockserver/mockserver:latest`
- **Concept:** API mocking and service virtualization for testing.
- **Configuration:** Initializes with a configuration file to set up expectations and behaviors.
- **Ports:** `1080:1080`

#### 3. **Zookeeper**

- **Image:** `confluentinc/cp-zookeeper:5.4.0`
- **Concept:** Distributed coordination service used by Kafka.
- **Configuration:** Client port and tick time for communication.
- **Ports:** `2181:2181`

#### 4. **Kafka Broker**

- **Image:** `confluentinc/cp-server:5.4.0`
- **Concept:** Distributed streaming platform for building real-time data pipelines and applications.
- **Configuration:** Connects to Zookeeper, sets up listeners, and configures metrics reporting.
- **Ports:** `9092:9092`

#### 5. **Volumes**

- **mongo-data:** Persistent storage for MongoDB data.

#### 6. **Networks**

- **host:** Uses the host network for the containers to communicate with the external environment.


# Getting Started

To get started with the Wishlist Service, follow these steps:

## Prerequisites

- Java 21
- Maven
- Docker and Docker Compose

## Running the Application

### Clone the Repository

```bash
git clone https://github.com/johnatannunes/wishlist-service.git

cd wishlist-sercive

```

### Build the Project:

Maven:

```bash 
./mvnw clean install
```

### Start the Docker Containers

```bash 
cd wishlist_stack && sudo docker-compose up -d
```

This command will start MongoDB, MockServer, Zookeeper, and Kafka services.

### Run the Application
###### You can run the application using your IDE or via the command line:
Maven

```bash 
./mvnw spring-boot:run
```

### API Endpoints Documentation 
###### Swagger UI
http://localhost:8080/swagger-ui/index.html#/

# License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## MIT License

Copyright (c) [2024]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.