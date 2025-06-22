#!/bin/bash

# Globetrotter All Services Startup Script
# This script starts all services using their individual docker-compose files

set -e

echo "🚀 Starting Globetrotter Services..."
echo "🌐 Creating shared network..."

# Create network if it doesn't exist
if ! docker network ls | grep -q "globetrotter-network"; then
    docker network create globetrotter-network
    echo "✅ Network created"
else
    echo "✅ Network already exists"
fi

# Check if .env file exists
if [ ! -f .env ]; then
    echo "⚠️  Warning: .env file not found!"
    echo "Please create a .env file with your API keys:"
    echo ""
    echo "OPENAI_API_KEY=your-openai-api-key-here"
    echo "OPENAI_API_MODEL=gpt-3.5-turbo"
    echo "DEEPSEEK_API_KEY=your-deepseek-api-key-here"
    echo "AMADEUS_API_CLIENTID=your-amadeus-client-id-here"
    echo "AMADEUS_API_CLIENTSECRET=your-amadeus-client-secret-here"
    echo ""
    read -p "Do you want to continue without .env file? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

# Stop any existing containers
echo "🛑 Stopping existing containers..."
docker-compose -f ../globetrotter-services/docker-compose.yml down 2>/dev/null || true
docker-compose -f ../globetrotter-mcp-server/docker-compose.yml down 2>/dev/null || true
docker-compose down 2>/dev/null || true

# Start logging infrastructure first
echo "📊 Starting logging infrastructure..."
cd ../globetrotter-logging
docker-compose up -d
cd ../globetrotter-bff

# Wait for logging to be ready
echo "⏳ Waiting for logging infrastructure..."
sleep 5

# Start services in order
echo "🔨 Starting globetrotter-services..."
cd ../globetrotter-services
docker-compose up -d
cd ../globetrotter-bff

# Wait for services to be healthy
echo "⏳ Waiting for globetrotter-services to be healthy on port 8080..."
for i in {1..30}; do
    if curl -f http://localhost:8080/actuator/health >/dev/null 2>&1; then
        echo "✅ globetrotter-services is healthy!"
        break
    fi
    echo "   Attempt $i/30 - globetrotter-services not ready yet..."
    sleep 2
done

echo "🔨 Starting globetrotter-mcp-server..."
cd ../globetrotter-mcp-server
docker-compose up -d
cd ../globetrotter-bff

# Wait for MCP server to be healthy
echo "⏳ Waiting for globetrotter-mcp-server to be healthy on port 8082..."
for i in {1..30}; do
    if curl -f http://localhost:8082/actuator/health >/dev/null 2>&1; then
        echo "✅ globetrotter-mcp-server is healthy!"
        break
    fi
    echo "   Attempt $i/30 - globetrotter-mcp-server not ready yet..."
    sleep 2
done

echo "🔨 Starting globetrotter-bff..."
docker-compose up -d

# Wait for BFF to be healthy
echo "⏳ Waiting for globetrotter-bff to be healthy on port 8084..."
for i in {1..30}; do
    if curl -f http://localhost:8084/actuator/health >/dev/null 2>&1; then
        echo "✅ globetrotter-bff is healthy!"
        break
    fi
    echo "   Attempt $i/30 - globetrotter-bff not ready yet..."
    sleep 2
done

echo ""
echo "🎉 All services started successfully!"
echo ""
echo "📊 Service Endpoints:"
echo "   globetrotter-services: http://localhost:8080"
echo "   globetrotter-mcp-server: http://localhost:8082"
echo "   globetrotter-bff: http://localhost:8084"
echo ""
echo "📊 Logging Infrastructure:"
echo "   Grafana: http://localhost:3000 (admin/admin)"
echo "   Loki: http://localhost:3100"
echo ""
echo "🔍 View logs in Grafana:"
echo "   1. Go to http://localhost:3000"
echo "   2. Login with admin/admin"
echo "   3. Go to Explore (compass icon)"
echo "   4. Select Loki datasource"
echo "   5. Query: {job=\"docker\"}"
echo ""
# Function to wait for service to be healthy
wait_for_service() {
    local service_name=$1
    local port=$2
    local max_attempts=30
    local attempt=1

    echo "⏳ Waiting for $service_name to be healthy on port $port..."

    while [ $attempt -le $max_attempts ]; do
        if curl -s -f "http://localhost:$port/actuator/health" > /dev/null 2>&1; then
            echo "✅ $service_name is healthy!"
            return 0
        fi

        echo "   Attempt $attempt/$max_attempts - $service_name not ready yet..."
        sleep 10
        attempt=$((attempt + 1))
    done

    echo "❌ $service_name failed to become healthy after $max_attempts attempts"
    return 1
}

# Start services in order
echo "🔨 Starting globetrotter-services..."
cd ../globetrotter-services
docker-compose up -d --build

# Wait for globetrotter-services to be healthy
wait_for_service "globetrotter-services" 8080

echo "🔨 Starting globetrotter-mcp-server..."
cd ../globetrotter-mcp-server
docker-compose up -d --build

# Wait for globetrotter-mcp-server to be healthy
wait_for_service "globetrotter-mcp-server" 8082

echo "🔨 Starting globetrotter-bff..."
cd ../globetrotter-bff
docker-compose up -d --build

# Wait for globetrotter-bff to be ready
wait_for_service "globetrotter-bff" 8084

# Check service status
echo "📊 Service Status:"
echo "=== globetrotter-services ==="
cd ../globetrotter-services && docker-compose ps
echo ""
echo "=== globetrotter-mcp-server ==="
cd ../globetrotter-mcp-server && docker-compose ps
echo ""
echo "=== globetrotter-bff ==="
cd ../globetrotter-bff && docker-compose ps

# Test the BFF endpoint
echo "🧪 Testing BFF endpoint..."
sleep 10
curl -s -X GET "http://localhost:8084/api/travel/flights?customerSearchQuery=I%20want%20to%20fly%20from%20LAX%20to%20JFK%20on%20January%2015th%202025%20with%201%20passenger%20in%20economy%20class" | head -c 200

echo ""
echo "✅ All services started successfully!"
echo ""
echo "🌐 Available endpoints:"
echo "   BFF API: http://localhost:8084/api/travel/flights"
echo "   Services API: http://localhost:8080/travel/flights/search"
echo "   MCP Server: http://localhost:8082"
echo ""
echo "📝 View logs:"
echo "   BFF: cd globetrotter-bff && docker-compose logs -f"
echo "   Services: cd globetrotter-services && docker-compose logs -f"
echo "   MCP Server: cd globetrotter-mcp-server && docker-compose logs -f"
echo ""
echo "🛑 Stop all services: ./stop-all-services.sh"
