# Role Based Access Control 

A Spring Boot application demonstrating Clean Architecture principles for a basic User Management System.

## 📌 Features

- Create Users and Roles
- Assign Roles to Users
- Retrieve User details with assigned roles
- Cleanly separated architecture (Domain, Use Case, Infrastructure, Config)
- In-memory H2 database
- Input validation and meaningful error handling
- Unit tested Use Case layer


## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven
- IntelliJ IDEA (recommended)

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/sejutiii/RBAC
   cd RBAC
   mvn clean install
   mvn spring-boot:run
2. Access the H2 console at- 
  ```bash
  http://localhost:8080/h2-console


