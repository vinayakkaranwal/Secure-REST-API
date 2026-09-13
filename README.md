# Secure REST API

## Overview
This project is a secure backend engine built with Spring Boot and MySQL, featuring user authentication, role-based authorization, CRUD operations, and OAuth2 client login.  
Includes Swagger documentation, actuator monitoring, and Postman-tested endpoints.

## Features
- User signup & login with JWT (access + refresh tokens)
- Role-based authorization: User and Admin
- CRUD operations:
  - Users (admin: create, update, delete, list)
  - Tasks (user scope: create, read, update, delete)
  - Admin can view & delete tasks
  - User can delete itself
- Granular method-level security with annotations
- Input validation & exception handling
- Swagger/OpenAPI documentation
- Actuator endpoints for monitoring
- OAuth2 client authentication(Google)
- Session management with JWT
- Spring Data JPA Auditing

## Tech Stack
- Backend: Spring Boot, Spring Security(JWT, OAuth2), Spring Data JPA, Hibernate, Java 21 
- Database: MySQL (managed with DBeaver)
- Build Tool: Apache Maven
- Documentation: Swagger/OpenAPI
- Utilities: Lombok, ModelMapper, Actuator, DevTools
- Testing: Postman

## Project Structure
-SecureRestApiApplication.java   # Main entry point

-advice/
 ├── ErrorResponse              # Standard error response model
 └── GlobalExceptionHandler     # Centralized exception handling

-config/
 ├── AppConfig                  # Provides ModelMapper bean 
 ├── JpaAuditingConfig          # JPA auditing setup
 ├── SwaggerConfig              # Swagger/OpenAPI configuration
 └── WebSecurityConfig          # Spring Security + JWT config

-controllers/
 ├── AdminController            # Admin endpoints (manage users)
 ├── AuthController             # Signup/Login and refresh endpoints
 ├── TaskController             # Task CRUD endpoints
 └── UserController             # User self‑delete endpoint

-dto/
 ├── LoginDTO                   # Login request payload
 ├── LoginResponseDTO           # Login response with JWT
 ├── SignupDTO                  # Signup request payload
 ├── TaskDTO                    # Task data transfer object
 └── UserDTO                    # User DTO and SignupResponseDTO

-entities/
 ├── enums/
 │    ├── Permissions           # Enum for granular permissions
 │    └── Roles                 # Enum for roles (USER, ADMIN)
 ├── AuditableEntity            # Base entity with audit fields
 ├── SessionEntity              # Session tracking entity
 ├── Task                       # Task entity
 └── User                       # User entity

-exceptions/
 └── ResourceNotFoundException  # Custom exception

-filter/
 └── JWTAuthFilter              # JWT authentication filter

-handler/
 └── OAuth2SuccessHandler       # OAuth2 login success handler

-repositories/
 ├── SessionRepo                # Repository for sessions
 ├── TaskRepo                   # Repository for tasks
 └── UserRepositorie            # Repository for users

-services/
 ├── AuthService                # Signup/Login and refresh logic
 ├── JwtService                 # JWT generation/validation
 ├── SessionService             # Session management
 ├── TaskService                # Task business logic
 └── UserService                # User business logic

-utils/
 ├── AuditorAwareImpl           # Provides current user for auditing
 ├── DataInitializer            # Seeds initial admin details
 └── PermissionMapping          # Maps roles to permissions
  
## API Endpoints
- Auth
  - POST /auth/signUp          → Register new user
  - POST /auth/logIn           → Login & receive JWT
  - POST /auth/refresh         → Refresh access token

- Tasks(User)
  - POST /task                 → Create task
  - GET /tasks/{user id}       → Get all tasks    
  - PUT /tasks/{task id}       → Update task      
  - DELETE /tasks/{task id}    → Delete task      

- User
  - DELETE /{username}/delete  → User delete itself 

- User and Task (Admin)
  - GET /admin                 → List all users
  - GET /admin/{user id}       → Get user
  - PUT /admin/{user id}       → Update user
  - DELETE /users/{user id}    → Delete user
  - GET /tasks/{user id}       → View all tasks
  - DELETE /tasks/{task id}    → Delete any task


## Testing
- Use Postman or Swagger UI 
- JWT required for protected endpoints


## Screenshots
- Swagger UI
<img width="1920" height="1020" alt="Screenshot 2026-09-13 000240" src="https://github.com/user-attachments/assets/e0cae891-7b2f-428e-9941-4f78757ce00b" />
<img width="1920" height="1020" alt="Screenshot 2026-09-13 000402" src="https://github.com/user-attachments/assets/717a14ed-25d2-41a2-baf1-b48efefbe12e" />
<img width="1920" height="1020" alt="Screenshot 2026-09-13 000411" src="https://github.com/user-attachments/assets/b484df5b-d66c-4b05-954d-215a486a77d6" />
<img width="1920" height="1020" alt="Screenshot 2026-09-13 000422" src="https://github.com/user-attachments/assets/e064c277-2bc5-41f5-9d4c-467f52b4dc9b" />
<img width="1920" height="1020" alt="Screenshot 2026-09-13 000545" src="https://github.com/user-attachments/assets/0e02f77e-bfe1-4caa-969e-ac940c7859d0" />

- Postman results
<img width="1920" height="1020" alt="Screenshot 2026-09-11 222854" src="https://github.com/user-attachments/assets/1dbc5a02-57e8-4cf5-93ab-b435d78047a6" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 223348" src="https://github.com/user-attachments/assets/532b80e2-1127-4372-bc0d-8f0415cbabf1" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 224521" src="https://github.com/user-attachments/assets/16a0199d-abbd-4a68-88c6-7e8c93ea563f" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 223920" src="https://github.com/user-attachments/assets/cdf08581-67c0-4221-9374-81c59d18ff56" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 224230" src="https://github.com/user-attachments/assets/e34816fe-fb2d-4d7e-b293-677d3c3aee2e" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 224718" src="https://github.com/user-attachments/assets/69e75783-593d-4cb4-a4cb-5fc7472f573b" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 224852" src="https://github.com/user-attachments/assets/27480b9f-45c3-4890-a157-de6da635994d" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 225142" src="https://github.com/user-attachments/assets/63b29ee0-2d5d-42be-9d56-b1a1bf532c87" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 225641" src="https://github.com/user-attachments/assets/2a5f3b89-4b7d-4e0a-9bb6-f9ccd6022c17" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 225749" src="https://github.com/user-attachments/assets/e18130dd-0900-44b7-ab0f-39514e7b403f" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 230111" src="https://github.com/user-attachments/assets/d9fbabf2-b2b7-4d9b-9bd6-5be5a5d7ec4d" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 230320" src="https://github.com/user-attachments/assets/20b6075e-a806-41b5-b7fe-afeb9814d89c" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 230500" src="https://github.com/user-attachments/assets/e55b318d-353c-4961-b746-a3a2f611cb93" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 231451" src="https://github.com/user-attachments/assets/b039abc2-79dd-46a0-8cd9-8f8ebf83b3e0" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 231620" src="https://github.com/user-attachments/assets/39837e6e-ac0a-494c-8358-e545e61df147" />

- OAuth2 login result
<img width="1920" height="1020" alt="Screenshot 2026-09-11 232340" src="https://github.com/user-attachments/assets/3a93fc82-75ca-408e-8b6e-424da0ad632a" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 232350" src="https://github.com/user-attachments/assets/55db1655-719b-4257-817e-042d3f2300dc" />
<img width="1920" height="1020" alt="Screenshot 2026-09-11 232749" src="https://github.com/user-attachments/assets/7feb0145-284e-44d9-8164-fcc7e681dbb8" />
<img width="1920" height="1020" alt="Screenshot 2026-09-12 021128" src="https://github.com/user-attachments/assets/e05d3ace-4c1a-4215-bc63-04285718e3b4" />
  
- Actuator monitoring
<img width="1920" height="1020" alt="Screenshot 2026-09-12 233130" src="https://github.com/user-attachments/assets/7c1d3db1-cef0-41f4-8ecf-ae0bb7c916f9" />
<img width="1920" height="1020" alt="Screenshot 2026-09-12 233245" src="https://github.com/user-attachments/assets/a39537db-fbed-4398-bd13-4f919bf74db3" />
<img width="1920" height="1020" alt="Screenshot 2026-09-12 233500" src="https://github.com/user-attachments/assets/e2826860-d79e-4ab7-8567-38b7c05ba13c" />

## Future Improvements
- Dockerization
- OAuth2 social login (GitHub)
- CI/CD pipeline integration
- Spring Boot Testing (JUint)
