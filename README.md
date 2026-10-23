# 💇 Salon Booking Platform Microservices

<p align="center">
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&weight=600&size=26&pause=1000&color=8B5CF6&center=true&vCenter=true&width=800&lines=Salon+Appointment+Booking+Platform;Spring+Boot+Microservices+Architecture;React+%7C+PostgreSQL+%7C+RabbitMQ;Secure+Bookings+and+Online+Payments" alt="Animated project title" />
</p>

<p align="center">
  A full-stack salon appointment booking platform built with Spring Boot microservices and React, featuring secure authentication, salon and service management, appointment scheduling, payment integration, and real-time notifications.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Database-PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Frontend-React-61DAFB?style=for-the-badge&logo=react&logoColor=black" alt="React" />
  <img src="https://img.shields.io/badge/Messaging-RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white" alt="RabbitMQ" />
  <img src="https://img.shields.io/badge/Containerization-Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Architecture-Microservices-blueviolet?style=flat-square" alt="Microservices architecture" />
  <img src="https://img.shields.io/badge/Security-Keycloak%20%7C%20JWT-4B0082?style=flat-square" alt="Security" />
  <img src="https://img.shields.io/badge/Payments-Razorpay-0C2451?style=flat-square" alt="Razorpay" />
</p>

---

## 📂 Related Repositories

- **Frontend — Salon Booking UI:** [Salon_Booking_Frontend](https://github.com/Dipeshsharma2005/Salon_Booking_Frontend)
- **Backend — Salon Booking Microservices:** [Salon_Booking_Microservices](https://github.com/Dipeshsharma2005/Salon_Booking_Microservices)

## 📌 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [Technology Stack](#-technology-stack)
- [System Architecture](#-system-architecture)
- [Microservices](#-microservices)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
- [Environment Configuration](#-environment-configuration)
- [Running with Docker](#-running-with-docker)
- [API Documentation](#-api-documentation)
- [Security](#-security)
- [Testing](#-testing)
- [Future Improvements](#-future-improvements)
- [Author](#-author)

---

## 🚀 Overview

The Salon Appointment Booking Platform is a full-stack application designed to connect customers with salons and simplify appointment management.

Customers can discover salons, explore available services, schedule appointments, and initiate payments. Salon owners can manage their salon profiles, service categories, offerings, and bookings.

The backend follows a **microservices architecture**, with independent Spring Boot services communicating through APIs and Feign clients. RabbitMQ supports messaging, while WebSocket technology enables real-time communication where configured.

The application uses **PostgreSQL for relational data storage**, Keycloak and JWT-based authentication mechanisms, and Razorpay for payment integration.

### 🎯 Project Goals

- Build a modular, maintainable backend using Spring Boot microservices.
- Separate business responsibilities into independent services.
- Implement authentication and role-based access.
- Manage salon listings, services, categories, and appointments.
- Integrate online payment processing.
- Support event-driven communication and real-time notifications.
- Containerize the application infrastructure with Docker.

---

## ✨ Key Features

### 👤 User Management and Authentication

- User registration and login.
- User profile retrieval.
- Token-based authentication.
- Keycloak integration for identity and access management.

### 💇 Salon Management

- Create and update salon profiles.
- Retrieve salon details.
- Search salons by city.
- Access salon-owner functionality.

### 🗂️ Category and Service Management

- Retrieve service categories.
- Associate categories with salons.
- Create and manage salon service offerings.
- Filter service offerings by category.
- Retrieve multiple services by their IDs.

### 📅 Appointment Booking

- Create salon appointments.
- Retrieve customer bookings.
- Retrieve salon-owner bookings.
- Check appointment slots by salon and date.
- Update booking status.
- Generate payment links during the booking flow.

### 💳 Payment Integration

- Integrate Razorpay payment functionality.
- Create payment links.
- Retrieve payment-order information.
- Process payment confirmation.

### 🔔 Messaging and Notifications

- RabbitMQ-based messaging infrastructure.
- WebSocket support for real-time communication.
- Notifications handled by a dedicated service.

### 🐳 Containerization

- Docker-based infrastructure.
- Docker Compose configuration for supported services.
- Containerized development environment.

---

## 🛠️ Technology Stack

| Category | Technologies |
|---|---|
| Backend | Java, Spring Boot |
| Architecture | Microservices |
| Service communication | REST APIs, OpenFeign |
| Database | **PostgreSQL** |
| Authentication | Keycloak, JWT |
| Messaging | RabbitMQ |
| Real-time communication | WebSocket |
| Frontend | React |
| Styling | Tailwind CSS, Material UI |
| State management | Redux |
| Form handling | Formik |
| Payment gateway | Razorpay |
| Containerization | Docker, Docker Compose |
| Backend IDE | IntelliJ IDEA |
| Frontend IDE | Visual Studio Code |

---

## 🏗️ System Architecture

The Salon Booking Platform uses a microservices architecture. The React frontend communicates with backend services through the API Gateway.

```text
                    ┌──────────────────────┐
                    │        USERS         │
                    │ Customer / Salon Owner│
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    React Frontend    │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      API Gateway     │
                    └──────────┬───────────┘
                               │
          ┌────────────────────┼────────────────────┐
          │                    │                    │
          ▼                    ▼                    ▼
  ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
  │ User Service │     │Salon Service │     │Booking Service│
  └──────────────┘     └──────────────┘     └──────┬───────┘
          │                    │                    │
          ▼                    ▼                    ▼
  ┌──────────────┐     ┌──────────────┐     ┌──────────────┐
  │  PostgreSQL  │     │  PostgreSQL  │     │Payment Service│
  └──────────────┘     └──────────────┘     └──────┬───────┘
                                                   │
                                                   ▼
                                          ┌────────────────┐
                                          │    Razorpay    │
                                          └────────────────┘

  Additional Backend Services:
  ├── Category Service
  ├── Service Offering Service
  ├── Review Service
  └── Notifications Service

  Infrastructure and Communication:
  ├── Eureka Server     → Service Discovery
  ├── Keycloak / JWT    → Authentication
  ├── RabbitMQ          → Asynchronous Messaging
  ├── WebSocket         → Real-time Updates
  └── OpenFeign         → Service-to-Service Communication


**Request flow:** Customer or salon owner → React Frontend → API Gateway → Relevant backend service.

**Note:** This is a conceptual overview. Database allocation and communication paths should be adjusted to match the actual implementation.
```

> **Note:** The diagram is a conceptual overview of the platform. Actual database allocation, gateway routing, authentication enforcement, and message flows depend on the configuration of each service. It does not imply that every microservice shares one PostgreSQL database.

---

## 🧩 Microservices

The backend repository contains the following service modules.

| Microservice | Responsibility |
|---|---|
| `ApiGatewayServer` | Entry point for routed client requests |
| `EurekaServer` | Service discovery and registration |
| `UserService` | User management and authentication-related operations |
| `SalonService` | Salon profile management and salon search |
| `CategoryService` | Salon service categories |
| `ServiceOffering` | Services provided by salons |
| `BookingService` | Appointment scheduling and booking management |
| `PaymentService` | Payment orders and Razorpay integration |
| `ReviewService` | Salon review-related functionality |
| `Notifications` | Notification-related functionality |

### Service Communication

The documented Feign clients include:

- `UserFeignClient` — communicates with user-related functionality.
- `SalonFeignClient` — retrieves salon information and supports salon-owner checks.
- `CategoryFeignClient` — retrieves category information.
- `ServiceOfferingFeignClient` — retrieves service offering details.
- `PaymentFeignClient` — communicates with payment functionality.

---

## 📂 Project Structure

The backend repository is organized into independent microservice modules.

```text
Salon_Booking_Microservices/
│
├── ApiGatewayServer/
├── BookingService/
├── CategoryService/
├── EurekaServer/
├── Notifications/
├── PaymentService/
├── ReviewService/
├── SalonService/
├── ServiceOffering/
├── UserService/
│
├── docker-compose/
│   └── default/
│
└── README.md
```

The frontend is maintained in a separate repository.

> The exact source folders, configuration files, Docker Compose filenames, and build files may vary depending on your local project structure.

---

## ⚙️ Prerequisites

Install the following tools before running the application:

- **Java JDK:** Version compatible with the Spring Boot projects.
- **Maven:** If the services use Maven.
- **Node.js and npm:** For the React frontend.
- **PostgreSQL:** Relational database.
- **Docker Desktop:** For containerized infrastructure and Keycloak setup.
- **Git:** For version control.
- **IntelliJ IDEA:** Recommended for backend development.
- **Visual Studio Code:** Recommended for frontend development.

Additional configuration may be required for RabbitMQ, Keycloak, Eureka, and the payment gateway.

---

## 📥 Getting Started

### 1. Clone the Backend Repository

```bash
git clone https://github.com/Dipeshsharma2005/Salon_Booking_Microservices.git

cd Salon_Booking_Microservices
```

### 2. Configure PostgreSQL

Install and start PostgreSQL, then create the database or databases required by your services.

Example database creation:

```sql
CREATE DATABASE salon_booking;
```

The name `salon_booking` is an example. Use the database names configured in your actual services.

### 3. Configure Database Connections

In the relevant service's `application.properties` or `application.yml`, configure the PostgreSQL connection.

Example `application.properties`:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
```

Example environment variables:

```env
DB_URL=jdbc:postgresql://localhost:5432/salon_booking
DB_USERNAME=postgres
DB_PASSWORD=your_local_password
```

Add the PostgreSQL JDBC driver to the relevant Maven module if it is not already present:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

See the [Spring Boot SQL database documentation](https://docs.spring.io/spring-boot/reference/data/sql.html) and [PostgreSQL JDBC documentation](https://jdbc.postgresql.org/documentation/).

**Important:** Apply database configuration to the services that actually use a relational database. If your services have separate database names or configurations, set them individually. Do not assume that all ten services use the same database.

### 4. Configure Keycloak

Start and configure Keycloak using the setup intended by your project.

Verify:

- Keycloak is running.
- The required realm and client configuration exists.
- Client IDs and relevant URLs match your application settings.
- Authentication and authorization settings are consistent with the backend.
- The frontend uses the correct authentication configuration.

### 5. Configure RabbitMQ

Start RabbitMQ using your local installation or the provided container configuration.

Verify that the broker URL, credentials, queues, exchanges, and bindings match your application settings.

### 6. Configure Payment Gateway Credentials

Set your Razorpay credentials as environment variables.

```env
RAZORPAY_KEY=your_razorpay_key
RAZORPAY_SECRET=your_razorpay_secret
```

Never commit actual credentials to GitHub.

### 7. Start the Backend Services

Open the backend project in IntelliJ IDEA and run the services according to their dependencies.

A typical startup order is:

1. PostgreSQL and RabbitMQ.
2. Keycloak.
3. Eureka Server.
4. API Gateway.
5. User, salon, category, and service offering services.
6. Booking, payment, review, and notification services.

The exact startup order depends on your project's configuration and whether services retry connections when dependencies are unavailable.

### 8. Start the Frontend

Clone your separate frontend repository and open it in a terminal.

```bash
git clone https://github.com/Dipeshsharma2005/Salon_Booking_Frontend.git

cd Salon_Booking_Frontend

npm install
npm start
```

If your frontend uses a different development script, check its `package.json`.

Configure the frontend's API base URL to point to the running API gateway.

---

## 🔐 Environment Configuration

Keep local configuration and credentials outside version control.

Example `.env.example`:

```env
# PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/salon_booking
DB_USERNAME=postgres
DB_PASSWORD=replace_with_local_password

# Razorpay
RAZORPAY_KEY=replace_with_razorpay_key
RAZORPAY_SECRET=replace_with_razorpay_secret

# RabbitMQ
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
```

These are illustrative names, not a guarantee that your application already reads every variable shown. Match them to the placeholders used in each service's configuration.

If Stripe is also configured in your project, define its required key separately. Do not publish either Stripe or Razorpay secrets.

### Security Checklist

- Add `.env` to `.gitignore`.
- Commit `.env.example` with placeholder values only.
- Never commit database passwords, API secrets, access tokens, or private keys.
- Avoid exposing payment secrets in React environment variables.
- Use suitable database credentials instead of default development credentials in production.
- Use HTTPS and secure production configuration where applicable.

---

## 🐳 Running with Docker

The repository contains a `docker-compose/default` directory. Inspect that directory for the actual Compose filename and available service definitions.

From the directory containing the relevant Compose file, run:

```bash
docker compose up -d
```

To check running containers:

```bash
docker compose ps
```

To inspect logs:

```bash
docker compose logs -f
```

To stop the containers:

```bash
docker compose down
```

These commands assume that a valid Docker Compose file exists in the selected directory. The services included in Compose depend on your repository configuration.

Before running the complete application, verify that:

- PostgreSQL is accessible to the services.
- Keycloak has the required configuration.
- RabbitMQ is reachable.
- Service ports do not conflict with other applications.
- The gateway can reach the registered services.

---

## 📡 API Documentation

The following endpoints are based on the project's API documentation. Requests that require authentication should include the appropriate authorization header.

```http
Authorization: Bearer <access_token>
```

The base URL and port depend on your gateway and service configuration.

### 1. User Service

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/signup` | Register a user |
| `POST` | `/auth/login` | Authenticate a user |
| `GET` | `/auth/access-token/refresh-token/{refreshToken}` | Refresh an access token |
| `GET` | `/api/users/profile` | Retrieve the authenticated user's profile |
| `GET` | `/api/users/{userId}` | Retrieve user details by ID |

### 2. Salon Service

Base path: `/api/salons`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/salons` | Create a salon |
| `PUT` | `/api/salons/{salonId}` | Update a salon |
| `GET` | `/api/salons` | Retrieve salons |
| `GET` | `/api/salons/{salonId}` | Retrieve a salon by ID |
| `GET` | `/api/salons/search?city={city}` | Search salons by city |
| `GET` | `/api/salons/owner` | Retrieve salon-owner-related information |

### 3. Category Service

Base path: `/api/categories`

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/categories` | Retrieve all categories |
| `GET` | `/api/categories/salon/{id}` | Retrieve categories for a salon |
| `GET` | `/api/categories/{id}` | Retrieve a category by ID |
| `DELETE` | `/api/categories/{id}` | Delete a category |
| `POST` | `/api/categories/salon-owner` | Create a category as a salon owner |

### 4. Service Offering

Base path: `/api/service-offering`

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/service-offering/salon/{salonId}` | Retrieve services for a salon |
| `GET` | `/api/service-offering/salon/{salonId}?categoryId={categoryId}` | Filter salon services by category |
| `GET` | `/api/service-offering/{serviceId}` | Retrieve a service by ID |
| `GET` | `/api/service-offering/list/{ids}` | Retrieve services by multiple IDs |
| `POST` | `/api/service-offering/salon-owner` | Create a service as a salon owner |
| `PUT` | `/api/service-offering/salon-owner/{serviceId}` | Update a service as a salon owner |

### 5. Booking Service

Base path: `/api/bookings`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/bookings` | Create a booking and initiate the payment flow |
| `GET` | `/api/bookings/customer` | Retrieve bookings for a customer |
| `GET` | `/api/bookings/salon` | Retrieve bookings for a salon owner |
| `GET` | `/api/bookings/report` | Retrieve booking report information |
| `GET` | `/api/bookings/slots/salon/{salonId}/date/{date}` | Retrieve available booking slots |
| `GET` | `/api/bookings/{bookingId}` | Retrieve a booking by ID |
| `PUT` | `/api/bookings/{bookingId}/status` | Update booking status |

Booking creation uses request details such as salon ID, payment method, service IDs, and appointment times according to the documented API contract.

### 6. Payment Service

Base path: `/api/payments`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/payments/create` | Create a payment link |
| `GET` | `/api/payments/{paymentOrderId}` | Retrieve a payment order |
| `PATCH` | `/api/payments/proceed` | Process payment confirmation |

The documented payment flow uses Razorpay. Exact request bodies, query parameters, and response schemas should be taken from the corresponding controller or API testing collection.

### 7. Review Service

The repository contains a `ReviewService` module. Its individual endpoints are not listed here because the available project documentation does not establish their exact paths and request schemas.

### 8. Notifications Service

The repository contains a `Notifications` module, along with RabbitMQ and WebSocket-related technology in the project stack. Refer to the service configuration and controllers for its exact endpoints, queue names, and event payloads.

---

## 🔒 Security

Security-related technologies and practices in the project include:

- **Keycloak:** Identity and access management.
- **JWT:** Token-based authentication.
- **Authorization headers:** Carry access tokens for protected operations.
- **Salon-owner restrictions:** Owner-specific operations use authentication and service-level validation.
- **Feign clients:** Allow services to retrieve and validate information from other services.
- **Environment variables:** Keep database and payment credentials out of source code.

Authentication and authorization should be verified for every protected endpoint, especially booking, payment, and salon-owner operations.

---

## 🧪 Testing

Before considering the application ready for deployment, verify the following workflows.

### Authentication

- Register a new user.
- Log in with valid credentials.
- Retrieve the user profile with a valid token.
- Verify that protected endpoints reject invalid or missing credentials.

### Salon and Service Management

- Create and update a salon.
- Search salons by city.
- Retrieve categories associated with a salon.
- Create and update service offerings.
- Verify that salon-owner operations enforce ownership checks.

### Booking

- Create a booking with valid salon and service details.
- Retrieve customer bookings.
- Retrieve salon bookings.
- Check available slots for a particular salon and date.
- Verify invalid requests and booking status transitions.

### Payments

- Create a payment link using test credentials.
- Retrieve the payment order.
- Verify the payment confirmation flow.
- Test invalid payment IDs and failed payment scenarios.

### Infrastructure

- Confirm PostgreSQL connectivity.
- Confirm Eureka service registration where configured.
- Confirm gateway routing.
- Verify RabbitMQ connectivity and message handling.
- Test notification delivery where implemented.
- Review logs for connection failures and unhandled exceptions.

Use test credentials and the payment provider's test mode during development.

---

## 🔮 Future Improvements

Potential improvements for the platform include:

- API documentation using Swagger/OpenAPI.
- Automated unit and integration tests.
- Centralized logging and distributed tracing.
- Health monitoring and readiness checks.
- CI/CD pipelines for automated testing and deployment.
- Database migrations using Flyway or Liquibase.
- Rate limiting and stronger production security controls.
- Improved service resilience, retries, and circuit breakers.
- A production deployment guide.
- Screenshots or GIFs demonstrating customer and salon-owner workflows.

---

## 👨‍💻 Author

**Dipesh Sharma**

B.Tech — Computer Science and Engineering

GitHub: [@Dipeshsharma2005](https://github.com/Dipeshsharma2005)

---

<p align="center">
  <img src="https://readme-typing-svg.demolab.com?font=Fira+Code&size=18&pause=1000&color=8B5CF6&center=true&vCenter=true&width=600&lines=Built+with+Spring+Boot+and+React;Designed+with+Microservices+Architecture" alt="Project footer animation" />
</p>

<p align="center">
  ⭐ If you find this project interesting, consider giving the repository a star!
</p>
