#!/bin/bash

# Start BFF Only Script
# This script starts only the BFF service, assuming other services are already running

echo "🚀 Starting Globetrotter BFF..."

# Check if .env file exists
if [ ! -f .env ]; then
    echo "⚠️  Warning: .env file not found!"
    echo "Please create a .env file with your API keys."
    exit 1
fi

# Check if the shared network exists
if ! docker network ls | grep -q globetrotter-network; then
    echo "❌ Error: globetrotter-network not found!"
    echo "Please start the other services first using ./start-all-services.sh"
    exit 1
fi

# Check if other services are running
echo "🔍 Checking if other services are running..."

# Check globetrotter-services
if ! curl -s -f "http://localhost:8080/actuator/health" > /dev/null 2>&1; then
    echo "❌ Error: globetrotter-services is not running on port 8080"
    echo "Please start it first: cd ../globetrotter-services && docker-compose up -d"
    exit 1
fi

# Check globetrotter-mcp-server
if ! curl -s -f "http://localhost:8082/actuator/health" > /dev/null 2>&1; then
    echo "❌ Error: globetrotter-mcp-server is not running on port 8082"
    echo "Please start it first: cd ../globetrotter-mcp-server && docker-compose up -d"
    exit 1
fi

echo "✅ All required services are running!"

# Stop existing BFF if running
echo "🛑 Stopping existing BFF container..."
docker-compose down 2>/dev/null || true

# Start BFF
echo "🔨 Starting BFF..."
docker-compose up -d --build

# Wait for BFF to be ready
echo "⏳ Waiting for BFF to be ready..."
max_attempts=30
attempt=1

while [ $attempt -le $max_attempts ]; do
    if curl -s -f "http://localhost:8084/actuator/health" > /dev/null 2>&1; then
        echo "✅ BFF is ready!"
        break
    fi

    echo "   Attempt $attempt/$max_attempts - BFF not ready yet..."
    sleep 10
    attempt=$((attempt + 1))
done

if [ $attempt -gt $max_attempts ]; then
    echo "❌ BFF failed to become ready after $max_attempts attempts"
    exit 1
fi

# Test the BFF endpoint
echo "🧪 Testing BFF endpoint..."
sleep 5
curl -s -X GET "http://localhost:8084/api/travel/flights?customerSearchQuery=I%20want%20to%20fly%20from%20LAX%20to%20JFK%20on%20January%2015th%202025%20with%201%20passenger%20in%20economy%20class" | head -c 200

echo ""
echo "✅ BFF started successfully!"
echo ""
echo "🌐 BFF API: http://localhost:8084/api/travel/flights"
echo "📝 View logs: docker-compose logs -f"
