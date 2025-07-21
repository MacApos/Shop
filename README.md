## About The Project

This is a simple shopping platform with handmade crochet products like hats, sweaters, rugs, bags or purses. Users can register,
log in, browse products, and manage a shopping cart.

## Features

- User registration and login
- JWT-based authentication
- Add/remove products to/from shopping cart
- View product category
- View current user cart

## Technologies

- Java 17
- Spring Boot 3.4.2
- Spring Data JPA
- Spring Security
- H2 Database Engine 2.3.232
- AssertJ 3.26.3

## Installation

1. Clone the repository
   ```sh
   git clone https://github.com/MacApos/GitHubAPIClient.git
   ```
2. Go to the project's main directory
   ```sh
   cd GitHubAPIClient
   ```
3. Run Spring Boot application
   ```sh
   ./mvnw spring-boot:run
   ```

## API Endpoints
- `/register` - this is a starting point for new customers. Registration will be followed by an email with a
  confirmation link. Activating it is required to enable the user account.
- `/login` - is done with email and password. Users with inactive accounts will not be authenticated.
  A valid registration or login process will set a JWT cookie with a max age of 7 days.
- `/user` - will show user information and allow modifying it.
- `/categories` - used for grouping products. They are organized into a hierarchical tree. Extend the path with /all to
  simply list them, or /hierarchy to check their structure.
- `/products` - can be listed by categories, paginated, and sorted.
  Users with the admin role can perform CRUD operations on categories and products.
- `/cart-items` - used for storing products saved by the user. They require a user role to be created and manipulated.