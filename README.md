# Employee Management System

A full-stack Employee Management application built using **Spring Boot**, **Spring Security (JWT)**, **Spring Data JPA**, **Hibernate**, **MySQL**, and **Angular 20 (Standalone Components)**.

---

## Features

### Authentication & Authorization
- JWT-based Authentication
- Role-Based Access Control (RBAC)
- Admin and User roles
- Protected API endpoints using Spring Security

### Employee Management
- Create Employee
- View Employees
- Update Employee
- Delete Employee
- Search Employees
- Pagination
- Sorting

### Department Management
- Create Department
- View Departments
- Update Department
- Delete Department
- Search Departments
- Pagination
- Sorting

### Dashboard
- Total Employees
- Total Departments
- Average Salary
- Highest Salary

### API Documentation
- Swagger / OpenAPI Integration

### Validation & Error Handling
- Request Validation using Jakarta Validation
- Global Exception Handling
- Custom API Response Structure

---

# Technology Stack

## Backend

| Technology | Version |
|------------|----------|
| Java | 21.0.9 |
| Spring Boot | 4.1.0 |
| Spring Security | 6.x |
| Spring Data JPA | 4.x |
| Hibernate | 6.x |
| MySQL | 8.x |
| JWT | JJWT |
| Maven | 3.x |
| Swagger OpenAPI | SpringDoc |

---

## Frontend

| Technology | Version |
|------------|----------|
| Angular | 20.3.x |
| TypeScript | 5.9.x |
| RxJS | 7.8.x |
| Zone.js | 0.15.x |
| Node.js | 24.x |
| npm | 11.x |

---

# Project Architecture

## Backend Layers

Controller
    ↓
Service
    ↓
Repository
    ↓
Database

Additional Layers:

DTO
Mapper (MapStruct)
Specification
Security
Exception Handling

---

# Security Flow

## Login Flow

1. User enters username and password.
2. Request sent to `/auth/login`.
3. Spring Security authenticates user.
4. JWT Token generated.
5. Token returned to frontend.
6. Angular stores token.
7. Token attached to every request using JWT Interceptor.
8. Spring Security validates token for every API request.

---

# Role Permissions

## ADMIN

### Employees
- Create
- Read
- Update
- Delete

### Departments
- Create
- Read
- Update
- Delete

---

## USER

### Employees
- Read Only

### Departments
- Read Only

---

# Database

## Employee

| Column | Type |
|----------|----------|
| id | BIGINT |
| name | VARCHAR |
| salary | DOUBLE |
| department_id | BIGINT |

---

## Department

| Column | Type |
|----------|----------|
| id | BIGINT |
| name | VARCHAR |

---

# REST APIs

## Authentication

| Method | Endpoint |
|----------|----------|
| POST | /auth/login |

---

## Employee APIs

| Method | Endpoint |
|----------|----------|
| GET | /employees |
| GET | /employees/{id} |
| POST | /employees |
| PUT | /employees/{id} |
| DELETE | /employees/{id} |

---

## Department APIs

| Method | Endpoint |
|----------|----------|
| GET | /departments |
| GET | /departments/{id} |
| POST | /departments |
| PUT | /departments/{id} |
| DELETE | /departments/{id} |

---

# Advanced Concepts Implemented

## Spring Boot
- Dependency Injection
- Bean Management
- Auto Configuration
- REST APIs
- DTO Pattern
- Global Exception Handling

## Spring Security
- Authentication
- Authorization
- JWT
- BCrypt Password Encoding
- Security Filter Chain

## Spring Data JPA
- Repositories
- Pagination
- Sorting
- Custom Queries
- Specifications

## Hibernate
- ORM Mapping
- Entity Relationships
- Lazy Loading

## Angular
- Standalone Components
- Routing
- Route Guards
- HTTP Client
- Interceptors
- FormsModule
- Two-Way Binding
- Structural Directives
- RxJS Debounce Search
---
