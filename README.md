# Patient Management System

A comprehensive microservices-based patient management system built with Spring Boot, featuring secure authentication, real-time analytics, and cloud-native infrastructure.

## 🏗️ Architecture Overview

This system follows a microservices architecture pattern with API Gateway routing, event-driven communication, and cloud-native deployment on AWS.

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Frontend      │───▶│   API Gateway    │───▶│  Load Balancer  │
│   Applications  │    │     (Port 4004)  │    │                 │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                │
                    ┌───────────┼───────────┐
                    │           │           │
            ┌───────▼──┐ ┌─────▼──┐ ┌─────▼─────┐
            │Auth      │ │Patient │ │Analytics  │
            │Service   │ │Service │ │Service    │
            │(4005)    │ │(4000)  │ │(4002)     │
            └──────────┘ └────────┘ └───────────┘
                    │           │           │
                    └───────────┼───────────┘
                                │
                        ┌───────▼──┐
                        │Billing   │
                        │Service   │
                        │(4001)    │
                        └──────────┘
                                │
                    ┌───────────┼───────────┐
                    │           │           │
            ┌───────▼──┐ ┌─────▼──┐ ┌─────▼─────┐
            │PostgreSQL│ │Apache  │ │   AWS     │
            │Database  │ │Kafka   │ │ Services  │
            └──────────┘ └────────┘ └───────────┘
```

## 🚀 Services Overview

| Service | Port | Technology | Description |
|---------|------|------------|-------------|
| **API Gateway** | 4004 | Spring Cloud Gateway | Request routing, load balancing, and cross-cutting concerns |
| **Auth Service** | 4005 | Spring Boot + JWT | Authentication, authorization, and user management |
| **Patient Service** | 4000 | Spring Boot + JPA | Patient data management and medical records |
| **Billing Service** | 4001/9001 | Spring Boot + gRPC | Billing operations and payment processing |
| **Analytics Service** | 4002 | Spring Boot + Kafka | Real-time analytics and reporting |

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.x
- **Language**: Java 17+
- **Build Tool**: Maven
- **Security**: Spring Security + JWT
- **Database**: PostgreSQL
- **Message Queue**: Apache Kafka (AWS MSK)
- **Communication**: REST APIs + gRPC

### Infrastructure
- **Cloud Provider**: AWS
- **Infrastructure as Code**: AWS CDK (Java)
- **Container Orchestration**: Amazon ECS Fargate
- **Database**: Amazon RDS (PostgreSQL)
- **Message Streaming**: Amazon MSK (Kafka)
- **Load Balancing**: Application Load Balancer
- **Service Discovery**: AWS Cloud Map

### Development Tools
- **IDE**: IntelliJ IDEA
- **Version Control**: Git + GitHub
- **Testing**: JUnit 5, TestContainers, WireMock
- **Documentation**: OpenAPI/Swagger

## 📋 Prerequisites

Before running this project, ensure you have:

- **Java 17+** installed
- **Maven 3.8+** installed
- **Docker** and Docker Compose
- **AWS CLI** configured
- **AWS CDK** installed (`npm install -g aws-cdk`)
- **PostgreSQL** (for local development)
- **IntelliJ IDEA** (recommended)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/patient-management.git
cd patient-management
```

### 2. Build All Services
```bash
# Build all modules
mvn clean install

# Or build specific service
cd patient-service
mvn clean install
```

### 3. Local Development Setup

#### Option A: Using Docker Compose (Recommended)
```bash
# Start infrastructure services
docker-compose up -d postgres kafka

# Run services individually in separate terminals
cd auth-service && mvn spring-boot:run
cd patient-service && mvn spring-boot:run
cd billing-service && mvn spring-boot:run
cd analytics-service && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
```

#### Option B: Using IntelliJ IDEA
1. Import the project as a Maven multi-module project
2. Configure run configurations for each service
3. Start services in the following order:
   - Infrastructure services (PostgreSQL, Kafka)
   - Auth Service
   - Patient Service
   - Billing Service
   - Analytics Service
   - API Gateway

### 4. Verify Services
```bash
# Check service health
curl http://localhost:4004/actuator/health  # API Gateway
curl http://localhost:4005/actuator/health  # Auth Service
curl http://localhost:4000/actuator/health  # Patient Service
curl http://localhost:4001/actuator/health  # Billing Service
curl http://localhost:4002/actuator/health  # Analytics Service
```

## ☁️ Cloud Deployment

### Deploy to AWS using CDK

```bash
# Navigate to infrastructure module
cd infrastructure

# Install dependencies and build
mvn clean compile

# Bootstrap CDK (first time only)
cdk bootstrap

# Deploy infrastructure
cdk deploy

# View deployed resources
cdk list
aws cloudformation describe-stacks --stack-name localstack
```

### Environment Variables

Each service requires specific environment variables:

#### Auth Service
```env
JWT_SECRET=your-jwt-secret
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/auth-service-db
SPRING_DATASOURCE_USERNAME=admin_user
SPRING_DATASOURCE_PASSWORD=your-password
```

#### Patient Service
```env
BILLING_SERVICE_ADDRESS=localhost
BILLING_SERVICE_GRPC_PORT=9001
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/patient-service-db
```

## 📊 API Documentation

### API Gateway Endpoints
- **Base URL**: `http://localhost:4004`
- **Health Check**: `GET /actuator/health`
- **API Docs**: `GET /swagger-ui.html`

### Service-Specific APIs
- **Auth Service**: Authentication and user management
  - `POST /api/auth/login` - User authentication
  - `POST /api/auth/register` - User registration
  - `GET /api/auth/profile` - Get user profile

- **Patient Service**: Patient data management
  - `GET /api/patients` - List all patients
  - `POST /api/patients` - Create new patient
  - `GET /api/patients/{id}` - Get patient by ID
  - `PUT /api/patients/{id}` - Update patient

- **Billing Service**: Billing operations
  - `GET /api/billing/invoices` - List invoices
  - `POST /api/billing/invoices` - Create invoice
  - `GET /api/billing/payments` - List payments

## 🧪 Testing

### Unit Tests
```bash
# Run all tests
mvn test

# Run tests for specific service
cd patient-service
mvn test
```

### Integration Tests
```bash
# Run integration tests
cd integration-tests
mvn test
```

### API Testing
Use the provided API request collections in the `api-requests` folder with tools like:
- **Postman**
- **IntelliJ HTTP Client**
- **curl**

## 📁 Project Structure

```
patient-management/
├── 📁 analytics-service/       # Analytics and reporting service
│   ├── 📁 src/main/java/
│   ├── 📁 src/main/resources/
│   └── 📄 pom.xml
├── 📁 api-gateway/            # API Gateway service
│   ├── 📁 src/main/java/
│   ├── 📁 src/main/resources/
│   └── 📄 pom.xml
├── 📁 api-requests/           # API testing collections
├── 📁 auth-service/           # Authentication service
│   ├── 📁 src/main/java/
│   ├── 📁 src/main/resources/
│   └── 📄 pom.xml
├── 📁 billing-service/        # Billing and payment service
│   ├── 📁 src/main/java/
│   ├── 📁 src/main/resources/
│   └── 📄 pom.xml
├── 📁 grpc-requests/          # gRPC testing files
├── 📁 infrastructure/         # AWS CDK infrastructure code
│   ├── 📁 src/main/java/
│   └── 📄 pom.xml
├── 📁 integration-tests/      # End-to-end integration tests
│   ├── 📁 src/test/java/
│   └── 📄 pom.xml
├── 📁 patient-service/        # Patient management service
│   ├── 📁 src/main/java/
│   ├── 📁 src/main/resources/
│   └── 📄 pom.xml
├── 📄 .gitignore
├── 📄 README.md
├── 📄 docker-compose.yml     # Local development setup
└── 📄 pom.xml                # Parent POM
```

## 🔧 Development Guidelines

### Code Style
- Follow Java naming conventions
- Use Spring Boot best practices
- Implement proper error handling
- Add comprehensive logging
- Write unit and integration tests

### Git Workflow
```bash
# Create feature branch
git checkout -b feature/new-feature

# Make changes and commit
git add .
git commit -m "feat: add new feature"

# Push and create PR
git push origin feature/new-feature
```

### Database Migrations
- Use Flyway for database migrations
- Place migration files in `src/main/resources/db/migration/`
- Follow naming convention: `V1__Initial_schema.sql`

## 🚨 Troubleshooting

### Common Issues

#### Services Not Starting
```bash
# Check if ports are available
netstat -tulpn | grep :4004

# Check logs
docker logs patient-management-postgres
```

#### Database Connection Issues
```bash
# Verify database is running
docker exec -it patient-management-postgres psql -U admin_user -d auth-service-db

# Check connection string format
jdbc:postgresql://localhost:5432/database-name
```

#### AWS Deployment Issues
```bash
# Check CDK diff
cdk diff

# Destroy and redeploy
cdk destroy
cdk deploy
```

## 📈 Monitoring and Observability

### Health Checks
All services expose health endpoints:
- `GET /actuator/health`
- `GET /actuator/info`
- `GET /actuator/metrics`

### Logging
- Structured logging with JSON format
- Centralized logging with ELK stack (planned)
- Request tracing with correlation IDs

## 🤝 Contributing

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'feat: add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Pull Request Guidelines
- Provide clear description of changes
- Include relevant tests
- Update documentation if needed
- Ensure CI/CD pipeline passes

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Contact

- **Project Maintainer**: Abdul Wahid
- **Email**: abdulwahid.dev1@gmail.com
- **GitHub**: @wahidbabar(https://github.com/wahidbabar)

## 🙏 Acknowledgments

- Spring Boot team for the excellent framework
- AWS for cloud infrastructure services
- Open source community for various libraries and tools

---

**Built with ❤️ for modern healthcare management**
