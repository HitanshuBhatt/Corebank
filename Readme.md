#  CoreBank — Core Banking Web Application

[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.4-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-blue?logo=springsecurity)](https://spring.io/projects/spring-security)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?logo=mysql)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-red?logo=apachemaven)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

> A full-stack core banking web application built with **Java 17**, **Spring Boot 3**, and **Thymeleaf** — featuring secure JWT authentication, account management, fund transfers, and PDF statement generation.

---

##  Table of Contents

- [Project Description](#-project-description)
- [Key Features](#-key-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Usage](#-usage)
- [Sample Outputs](#-sample-outputs)
- [Technical Skills Demonstrated](#-technical-skills-demonstrated)
- [Roadmap & Improvements](#-roadmap--improvements)
- [Contributing](#-contributing)
- [Contact](#-contact)

---

## Project Description

**CoreBank** is a full-stack banking web application that simulates core banking operations including user registration, secure login, account management, fund transfers, and transaction history with downloadable PDF statements.

This project was built to demonstrate production-ready backend development practices using the **Spring ecosystem**, including security best practices with **JWT-based authentication**, data persistence via **Spring Data JPA** with a **MySQL** database, and a responsive frontend rendered through **Thymeleaf** templates with HTML/CSS/JavaScript.

The application is designed to model the workflows of a real-world retail banking system, making it relevant to fintech, enterprise, and financial services roles in the Canadian tech market.

---

##  Key Features

- ** Secure Authentication** — JWT-based stateless authentication with Spring Security; passwords are never stored in plain text
- ** User Registration & Login** — Full customer onboarding flow with server-side validation
- ** Account Management** — Create and manage bank accounts; view balances and account details
- ** Fund Transfers** — Transfer funds between accounts with transactional integrity
- ** Transaction History** — View a complete log of all past transactions per account
- ** PDF Statement Generation** — Download account statements as formatted PDF reports using OpenPDF
- ** Input Validation** — Bean Validation (JSR-380) enforced on all forms to ensure data integrity
- **️ Responsive UI** — Server-side rendered HTML templates via Thymeleaf with CSS/JavaScript

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Java 17 |
| **Framework** | Spring Boot 3.2.4 |
| **Web Layer** | Spring MVC, Thymeleaf |
| **Security** | Spring Security, JWT (jjwt 0.11.5) |
| **Persistence** | Spring Data JPA, Hibernate |
| **Database** | MySQL 8.x |
| **PDF Generation** | OpenPDF (LibrePDF 1.3.30) |
| **Build Tool** | Apache Maven (Maven Wrapper included) |
| **Code Generation** | Lombok 1.18.30 |
| **Testing** | Spring Boot Test (JUnit 5) |
| **Frontend** | HTML5, CSS3, JavaScript (Thymeleaf templates) |

---

## 📁 Project Structure

```
Corebank/
├── src/
│   └── main/
│       ├── java/com/corebank/
│       │   ├── config/          # Security & JWT configuration
│       │   ├── controller/      # MVC Controllers (auth, account, transaction)
│       │   ├── model/           # JPA Entity classes
│       │   ├── repository/      # Spring Data JPA Repositories
│       │   ├── service/         # Business logic layer
│       │   └── CoreBankApplication.java
│       └── resources/
│           ├── templates/       # Thymeleaf HTML templates
│           ├── static/          # CSS, JS, images
│           └── application.properties
├── .mvn/wrapper/                # Maven wrapper scripts
├── pom.xml                      # Project dependencies & build config
├── mvnw / mvnw.cmd              # Cross-platform Maven wrapper
└── README.md
```

---

## ✅ Prerequisites

Ensure the following are installed before running the application:

- **Java 17+** — [Download JDK](https://adoptium.net/)
- **MySQL 8.x** — [Download MySQL](https://dev.mysql.com/downloads/)
- **Maven 3.8+** *(or use the included `mvnw` wrapper — no installation needed)*
- **Git**

---

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/HitanshuBhatt/Corebank.git
cd Corebank
```

### 2. Configure the Database

Create a MySQL database:

```sql
CREATE DATABASE corebank_db;
```

### 3. Configure Application Properties

Edit `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/corebank_db
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT Secret (use a strong secret in production)
jwt.secret=your-256-bit-secret-key-here
jwt.expiration=86400000
```



### 4. Build and Run

Using the Maven wrapper (no Maven installation required):

```bash
# On macOS / Linux
./mvnw spring-boot:run

# On Windows
mvnw.cmd spring-boot:run
```

Or build a JAR and run it:

```bash
./mvnw clean package
java -jar target/corebank-0.0.1-SNAPSHOT.jar
```

### 5. Access the Application

Open your browser and navigate to:

```
http://localhost:8080
```

---

## Usage

### Register a New Account

1. Navigate to `http://localhost:8080/register`
2. Fill in your name, email, and password
3. Submit the form — your bank account is created automatically

### Login

1. Go to `http://localhost:8080/login`
2. Enter your registered credentials
3. On success, you are redirected to your dashboard

### Transfer Funds

1. From the dashboard, click **Transfer**
2. Enter the recipient's account number and the amount
3. Confirm — the transaction is recorded immediately

### Download a PDF Statement

1. Navigate to **Transaction History**
2. Click **Download Statement**
3. A formatted PDF is generated and downloaded to your device

---

| Feature | Description |
|---|---|
| Dashboard | Displays account balance and recent transactions |
| Transfer Page | Form with validation for fund transfers |
| Transaction History | Paginated list of all transactions |
| PDF Statement | Downloadable, formatted account statement |

---

##  Technical Skills Demonstrated

This project highlights competencies that are directly relevant to **software engineering roles** in Canada's tech and financial services sectors:

| Skill Area                        | What This Project Shows                                                                  |
|-----------------------------------|------------------------------------------------------------------------------------------|
| **Backend Development**           | RESTful MVC architecture using Spring Boot 3 and Java 17                                 |
| **Security Engineering**          | Stateless JWT authentication, password encoding, Spring Security configuration           |
| **Database Design**               | Relational schema design with JPA/Hibernate ORM, MySQL                                   |
| **API & Input Validation**        | Bean Validation (JSR-380) enforced at the service layer                                  |
| **PDF Generation**                | Dynamic document creation using OpenPDF (LibrePDF)                                       |
| **Full-Stack Development**        | Server-side rendering with Thymeleaf, HTML5, CSS3, JavaScript                            |
| **Build & Dependency Management** | Maven project structure, dependency management, Maven wrapper                            |
| **Code Quality**                  | Lombok for reduced boilerplate, layered architecture (Controller → Service → Repository) |
| **Version Control**               | Git/GitHub workflow                                                                      |

---

##  Roadmap & Improvements

Planned features and improvements to enhance this project further:

- [ ] **Unit & Integration Tests** — Add JUnit 5 tests for service and controller layers; target 70%+ code coverage
- [ ] **Docker Support** — Add `Dockerfile` and `docker-compose.yml` for one-command local setup
- [ ] **CI/CD Pipeline** — GitHub Actions workflow for automated build, test, and deployment
- [ ] **Environment-based Configuration** — Externalize secrets via environment variables; add `.env.example`
- [ ] **API Documentation** — Integrate Swagger/OpenAPI 3 for self-documenting REST endpoints
- [ ] **Pagination** — Paginate transaction history for performance at scale
- [ ] **Email Notifications** — Spring Mail integration for transaction confirmation emails
- [ ] **Admin Dashboard** — Separate admin panel for managing all users and accounts
- [ ] **Deployment Guide** — Instructions for deploying to AWS EC2 / Render / Railway

---

##  Contributing

Contributions are welcome! To contribute:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "feat: add your feature description"`
4. Push to your branch: `git push origin feature/your-feature-name`
5. Open a Pull Request with a clear description of your changes

Please follow [Conventional Commits](https://www.conventionalcommits.org/) for commit messages.

---

##  Contact

**Hitanshu Bhatt**

- 🐙 GitHub: [@HitanshuBhatt](https://github.com/HitanshuBhatt)
- 💼 LinkedIn: https://www.linkedin.com/in/hitanshu-bhatt/


---

> *Built with using Java & Spring Boot. Open to feedback and collaboration.*