# DocManager Service

## Overview
DocManager Service is a Spring Boot application designed to manage documents and authors.
It provides APIs to add, update, and delete documents and authors.
The application includes security and authentication mechanisms to ensure that only admin users can modify data, while other users can only retrieve data.
Pagination is used when fetching data for performance optimization.
The service is documented using Swagger and runs on Java 21 with PostgreSQL as the database.
Docker Compose is used to build and run the service.

## Features
- **APIs for Documents and Authors**: Add, update, and delete documents and authors.
- **Security and Authentication**: Only admin users can modify data; other users can only retrieve data.
- **Pagination**: Used for performance optimization when fetching data.
- **Swagger Documentation**: APIs are documented using Swagger.
- **Global Exception Handling**: Consistent error handling across the application.
- **Unit Tests**: Core logic is covered by unit tests.

## Technologies
- **Spring Boot**: Java 21
- **PostgreSQL**: Database
- **Docker Compose**: Build and run the service
- **Swagger**: API documentation

## Getting Started

### Prerequisites
- Docker
- Docker Compose
- Java 21
- Maven

### Configuration
The default user email and password are configurable in the `application.properties` file. The application supports two roles: `ADMIN` and `CUSTOMER`.

### Running the Application
1. **Clone the repository**:
    ```sh
    git clone https://github.com/ramimohsen/DocManager
    cd repository-directory
    ```

2. **Build the Docker images and start the containers**:
    ```sh
    docker-compose up --build
    ```

3. **Access the application**:
    - The Spring Boot application will be running on `http://localhost:8080`.
    - The PostgreSQL database will be accessible on `localhost:5432`.

### Swagger Documentation
Access the Swagger UI to explore the APIs:
[Swagger UI](http://localhost:8080/swagger-ui.html)

## API Endpoints

### Documents
- **Create Document**: `POST /api/v1/document`
- **Update Document**: `PUT /api/v1/document/{documentId}`
- **Delete Document**: `DELETE /api/v1/document/{documentId}`
- **Get Document by ID**: `GET /api/v1/document/{documentId}`
- **Get Document by Title**: `GET /api/v1/document/title/{title}`
- **Get All Documents**: `GET /api/v1/document`

### Authors
- **Create Author**: `POST /api/v1/author`
- **Update Author**: `PUT /api/v1/author/{authorId}`
- **Delete Author**: `DELETE /api/v1/author/{authorId}`
- **Get Author by ID**: `GET /api/v1/author/{authorId}`
- **Get All Authors**: `GET /api/v1/author`

## Security
- **Admin Users**: Can add, update, and delete documents and authors.
- **Customer Users**: Can only retrieve documents and authors.

## Global Exception Handling
The application uses global exception handling to provide consistent error responses.

## Unit Tests
Unit tests are provided to cover the core logic of the application. Run the tests using Maven:
```sh
mvn test