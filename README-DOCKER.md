# Globetrotter Distributed Docker Setup

This setup uses individual docker-compose files for each service, connected via a shared network. The BFF service can start the other services as dependencies.

## Architecture

- **globetrotter-services** (Port 8080) - Main API service with flight search
- **globetrotter-mcp-server** (Port 8082) - Model Context Protocol server
- **globetrotter-bff** (Port 8084) - Backend for Frontend

## Prerequisites

- Docker and Docker Compose installed
- API keys for the services you want to use

## Environment Variables

Create a `.env` file in the globetrotter-bff directory with:

```bash
# OpenAI Configuration
OPENAI_API_KEY=your-openai-api-key-here
OPENAI_API_MODEL=gpt-3.5-turbo

# DeepSeek Configuration (optional)
DEEPSEEK_API_KEY=your-deepseek-api-key-here

# Amadeus Flight API Configuration
AMADEUS_API_CLIENTID=your-amadeus-client-id-here
AMADEUS_API_CLIENTSECRET=your-amadeus-client-secret-here
```

## Running All Services

### Option 1: Use the startup script (Recommended)
```bash
./start-all-services.sh
```

### Option 2: Start services individually
```bash
# 1. Create shared network
docker network create globetrotter-network

# 2. Start globetrotter-services
cd ../globetrotter-services
docker-compose up -d --build

# 3. Start globetrotter-mcp-server
cd ../globetrotter-mcp-server
docker-compose up -d --build

# 4. Start globetrotter-bff
cd ../globetrotter-bff
docker-compose up -d --build
```

## Running Individual Services

### Start only BFF (requires other services to be running)
```bash
# Option 1: Use the helper script (recommended)
./start-bff-only.sh

# Option 2: Manual start
docker-compose up -d --build
```

### Start only Services
```bash
cd ../globetrotter-services
docker-compose up -d --build
```

### Start only MCP Server
```bash
cd ../globetrotter-mcp-server
docker-compose up -d --build
```

## Stopping Services

### Stop all services
```bash
./stop-all-services.sh
```

### Stop individual services
```bash
# Stop BFF
docker-compose down

# Stop Services
cd ../globetrotter-services && docker-compose down

# Stop MCP Server
cd ../globetrotter-mcp-server && docker-compose down
```

## Service Dependencies

- **globetrotter-services**: No dependencies
- **globetrotter-mcp-server**: Depends on globetrotter-services
- **globetrotter-bff**: Depends on globetrotter-mcp-server

**Note**: Since each service has its own docker-compose file, dependencies are handled by the startup scripts rather than docker-compose `depends_on`.

## Health Checks

Each service has health checks configured:
- Services are considered healthy when their `/actuator/health` endpoint responds
- Health checks run every 30 seconds
- Services will restart automatically if they become unhealthy
- Startup scripts wait for services to be healthy before starting the next service

## API Endpoints

- **BFF API**: http://localhost:8084/api/travel/flights
- **Services API**: http://localhost:8080/travel/flights/search
- **MCP Server**: http://localhost:8082

## Testing

Test the BFF endpoint:
```bash
curl -X GET "http://localhost:8084/api/travel/flights?customerSearchQuery=I%20want%20to%20fly%20from%20LAX%20to%20JFK%20on%20January%2015th%202025%20with%201%20passenger%20in%20economy%20class"
```

## Viewing Logs

```bash
# BFF logs
docker-compose logs -f

# Services logs
cd ../globetrotter-services && docker-compose logs -f

# MCP Server logs
cd ../globetrotter-mcp-server && docker-compose logs -f
```

## Troubleshooting

1. **Check service status:**
   ```bash
   docker-compose ps
   ```

2. **Check network:**
   ```bash
   docker network ls
   docker network inspect globetrotter-network
   ```

3. **Check if services are healthy:**
   ```bash
   curl http://localhost:8080/actuator/health  # Services
   curl http://localhost:8082/actuator/health  # MCP Server
   curl http://localhost:8084/actuator/health  # BFF
   ```

4. **Restart a specific service:**
   ```bash
   docker-compose restart [service-name]
   ```

5. **Clean up everything:**
   ```bash
   ./stop-all-services.sh
   docker system prune -f
   ```

## Available Scripts

- **`start-all-services.sh`** - Starts all services in correct order with health checks
- **`start-bff-only.sh`** - Starts only BFF (checks if other services are running)
- **`stop-all-services.sh`** - Stops all services and cleans up

## Benefits of This Approach

✅ **Separation of Concerns**: Each service has its own docker-compose file
✅ **Independent Development**: Can work on services individually
✅ **Shared Network**: All services can communicate with each other
✅ **Flexible Deployment**: Can start services in any order or combination
✅ **Easy Maintenance**: Each service can be updated independently
✅ **Health Monitoring**: Proper health checks and dependency management
