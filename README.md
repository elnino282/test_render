# 🏢 Agent Management Backend

## 📋 Overview

Backend API cho hệ thống quản lý đại lý - Đồ án Software Engineering SE104.P21

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.4.5
- **Language**: Java 17
- **Database**: PostgreSQL (Production), H2 (Development)
- **Build Tool**: Maven
- **Deployment**: Render.com

## 🚀 Quick Start

### Local Development

```bash
# Clone repository
git clone <repository-url>
cd AgentManagementBE

# Run with H2 database
./mvnw spring-boot:run

# Access H2 Console: http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:agentmanagementdb
# Username: sa
# Password: (empty)
```

### Production Build

```bash
# Build for production
./mvnw clean package -DskipTests

# Run with production profile
java -Dspring.profiles.active=prod -jar target/AgentManagementBE-0.0.1-SNAPSHOT.jar
```

## 📊 API Endpoints

### Health Check
- `GET /api/health` - Application health status
- `GET /api/ping` - Simple ping endpoint
- `GET /actuator/health` - Spring Actuator health

### Core Endpoints
- `GET /agent/getAllAgents` - Lấy danh sách đại lý
- `POST /agent/addAgent` - Thêm đại lý mới
- `GET /product/getAllProducts` - Lấy danh sách sản phẩm
- `GET /salesReport/getAllSalesReports` - Báo cáo bán hàng
- `GET /admin/getAllAdmins` - Quản lý admin

## 🌐 Deploy to Render.com

### 1. Create PostgreSQL Database

1. Go to [Render Dashboard](https://dashboard.render.com/)
2. Click "New +" → "PostgreSQL"
3. Configure:
   - Name: `agentmanagement-db`
   - Database: `agentmanagement`
   - User: `agentmanagement_user`
   - Plan: Free

### 2. Deploy Web Service

1. Click "New +" → "Web Service"
2. Connect your GitHub repository
3. Configure:
   - **Build Command**: `./mvnw clean install -DskipTests`
   - **Start Command**: `java -Dspring.profiles.active=prod -jar target/AgentManagementBE-0.0.1-SNAPSHOT.jar`

### 3. Environment Variables

Set these in Render Dashboard:

```bash
SPRING_PROFILES_ACTIVE=prod
PORT=8080
DATABASE_URL=<Internal Database URL>
DATABASE_USERNAME=<Database Username>
DATABASE_PASSWORD=<Database Password>
```

## 🔧 Configuration

### Profiles

- **default**: H2 Database, Development mode
- **prod**: PostgreSQL Database, Production optimized

### Database Configuration

- **Development**: H2 in-memory database
- **Production**: PostgreSQL with optimized connection pool

## 📁 Project Structure

```
src/
├── main/
│   ├── java/org/example/AgentManagementBE/
│   │   ├── Controller/          # REST Controllers
│   │   ├── Service/             # Business Logic
│   │   ├── Repository/          # Data Access
│   │   ├── Model/               # Entity Classes
│   │   ├── CorsConfig.java      # CORS Configuration
│   │   ├── DatabaseConfig.java  # Database Configuration
│   │   └── AgentManagementBeApplication.java
│   └── resources/
│       ├── application.properties      # Development config
│       └── application-prod.properties # Production config
```

## 🧪 Testing

```bash
# Run tests
./mvnw test

# Build without tests
./mvnw clean package -DskipTests
```

## 📝 Environment Requirements

- Java 17+
- Maven 3.6+
- PostgreSQL 12+ (Production)

## 🔗 Useful Links

- [Render Dashboard](https://dashboard.render.com/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/) 