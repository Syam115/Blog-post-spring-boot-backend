# Blog App Backend

A Spring Boot REST API backend for a blogging platform with user authentication, post management, and commenting functionality.

## Features

- **User Authentication** - JWT-based authentication and registration
- **User Profiles** - Create and manage user profiles
- **Blog Posts** - Create, read, update, and delete blog posts
- **Comments** - Add comments to blog posts
- **Security** - Spring Security with JWT token validation
- **CORS Support** - Cross-Origin Resource Sharing configured
- **Role-Based Access** - User role-based authorization

## Technology Stack

- **Java 11+**
- **Spring Boot 2.x**
- **Spring Security**
- **JWT (JSON Web Tokens)**
- **Spring Data JPA**
- **Maven**
- **MySQL/PostgreSQL** (configurable)

## Project Structure

```
src/main/java/com/example/blog_app/
├── config/              # Configuration classes
│   ├── CorsConfig.java
│   ├── SecurityConfig.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtUtil.java
│   └── UserPrincipal.java
├── controller/          # REST API endpoints
│   ├── AuthController.java
│   ├── PostController.java
│   ├── CommentController.java
│   └── UserController.java
├── service/             # Business logic
│   ├── AuthService.java
│   ├── PostService.java
│   ├── CommentService.java
│   └── UserService.java
├── entity/              # JPA entities
│   ├── User.java
│   ├── Post.java
│   └── Comment.java
├── dto/                 # Data Transfer Objects
│   ├── LoginRequest.java
│   ├── SignupRequest.java
│   ├── PostRequest.java
│   ├── PostResponse.java
│   ├── CommentRequest.java
│   ├── CommentResponse.java
│   └── UserProfileResponse.java
└── repository/          # JPA repositories
    ├── UserRepository.java
    ├── PostRepository.java
    └── CommentRepository.java
```

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven 3.6+
- MySQL 5.7+ or PostgreSQL 10+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd blog-app-backend
   ```

2. **Configure the database**
   
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/blog_app
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The server will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/signup` - Register a new user
- `POST /api/auth/login` - Login user and get JWT token

### Users
- `GET /api/users/{id}` - Get user profile
- `PUT /api/users/{id}` - Update user profile
- `GET /api/users` - Get all users

### Posts
- `GET /api/posts` - Get all posts
- `GET /api/posts/{id}` - Get post by ID
- `POST /api/posts` - Create new post
- `PUT /api/posts/{id}` - Update post
- `DELETE /api/posts/{id}` - Delete post

### Comments
- `GET /api/posts/{postId}/comments` - Get comments for a post
- `POST /api/posts/{postId}/comments` - Add comment to post
- `PUT /api/comments/{id}` - Update comment
- `DELETE /api/comments/{id}` - Delete comment

## Authentication

This API uses JWT (JSON Web Tokens) for authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

## Configuration

Key configuration files:
- `application.properties` - Database, server, and application settings
- `SecurityConfig.java` - Spring Security configuration
- `CorsConfig.java` - CORS settings
- `JwtUtil.java` - JWT token generation and validation

## Building and Testing

```bash
# Build the project
mvn clean build

# Run tests
mvn test

# Build with Maven wrapper
./mvnw clean install
```
