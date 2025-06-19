# Globetrotter BFF (Backend for Frontend)

This is the Backend for Frontend (BFF) layer for the Globetrotter application. It acts as an intermediary between the frontend and backend services, providing a simplified API and handling cross-cutting concerns.

## Features

- API Gateway functionality
- Circuit breaking and resilience
- Request/response transformation
- Caching
- API documentation with OpenAPI/Swagger
- Metrics and monitoring

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Spring Boot 3.2.3
- Spring Cloud 2023.0.0

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/globetrotter-bff.git
   cd globetrotter-bff
   ```

2. Build the project:
   ```bash
   ./mvnw clean install
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on port 8081.

## Development

### Project Structure

```
src/main/java/com/globetrotter/bff/
├── client/          # Feign clients for backend services
├── controller/      # REST controllers
├── model/          # Data models
├── service/        # Business logic
└── config/         # Configuration classes
```

### Available Endpoints

- API Documentation: http://localhost:8081/swagger-ui.html
- Health Check: http://localhost:8081/actuator/health
- Metrics: http://localhost:8081/actuator/metrics

### Configuration

The application can be configured through `application.yml`. Key configurations include:

- Service URLs
- Timeouts
- Circuit breaker settings
- Metrics and monitoring

## Testing

Run the tests with:
```bash
./mvnw test
```

## Contributing

1. Create a feature branch
2. Make your changes
3. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.