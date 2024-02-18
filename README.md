# Product Wishlist Management Application

This is a Spring Boot application for managing product wishlists. The application has two roles: `ADMIN` and `USER`. The `ADMIN` role is hardcoded with the username `admin`, email `admin@gmail.com`, and password `admin`. Users must create their account first to use the application.

## Packages

The application is organized into several packages:

- `config`: Contains configuration classes for the application.
- `controller`: Contains controller classes that handle HTTP requests.
- `dto`: Contains Data Transfer Object classes used for transferring data between processes.
- `exception`: Contains custom exception classes for the application.
- `model`: Contains model classes that define the structure of the data in the application.
- `repository`: Contains Spring Data JPA repository interfaces for CRUD operations.
- `service`: Contains service classes that handle business logic.

## Setup

1. Clone the repository to your local machine.
```
https://github.com/adnan25042001/product-wishlist-management.git
```
2. Install MySQL and create a database named `product_wishlist`.

3. Update the `spring.datasource.username` and `spring.datasource.password` properties in the `application.properties` file with your MySQL username and password.

## Running the Application

You can run the application using the following command in the root directory of the project:
```
./mvnw spring-boot:run
```

The application will start and run on 
```
http://localhost:8000.
```

## Testing

You can run the tests for the application using the following command in the root directory of the project:
```
./mvnw test
```

## Configuration

The `application.properties` file contains the configuration for the application:

```bash
server.port=8000

jwt.secret=${jwt_secret_key}

spring.datasource.url=jdbc:mysql://localhost:3306/${db}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=${username}
spring.datasource.password=${password}

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## API Endpoints

The application provides several API endpoints for managing products and wishlists:

- POST `/api/auth/register` : Register a new user.
- POST `/api/auth/login` : Login a user.
- POST `/api/auth/admin/login`: Login an admin.
- GET `/api/products/` : Get all products (available for everybody).
- POST `/api/products/add` : Add a new product (only admin can add products).
- GET `/api/wishlists/` : Get the wishlist of the logged-in user.
- POST `/api/wishlists/` : Add a product to the wishlist of the logged-in user.
- POST `/api/wishlists/{id}` : Delete a product from the wishlist of the logged-in user.

## API Documentation

This application uses Swagger for API documentation. Swagger provides a set of open-source tools that use OpenAPI Specification, which can help you design, build, document, and consume REST APIs.

Once the application is running, you can view the Swagger UI by navigating to 
```
http://localhost:8000/swagger-ui/
``` 
in your web browser.

The Swagger UI provides a visual interface for seeing your API's resources, on which methods are available, how they're structured, and what they can accept as input and return as output. You can also make requests to your API directly from the Swagger UI.

Here's a brief overview of what you'll see in the Swagger UI:

- **Models:** These are the objects that your API sends and receives. In Swagger, you can view the model schema, an example model, and a description of the model fields.

- **Endpoints:** These are the paths that your API exposes. For each endpoint, you can see which HTTP methods are available, what parameters they accept, and what responses they can return. You can also expand each endpoint to view more details.

- **Try it out:** For each endpoint, there's a "Try it out" button. Clicking this button lets you fill out the parameters, send a request to the API, and view the response.

Remember to replace `http://localhost:8000/swagger-ui/` with the actual URL of your running application if it's hosted somewhere other than your local machine.
