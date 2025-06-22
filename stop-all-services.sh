#!/bin/bash

# Globetrotter All Services Stop Script
# This script stops all services using their individual docker-compose files

echo "🛑 Stopping Globetrotter Services..."

# Stop services in reverse order
echo "🛑 Stopping globetrotter-bff..."
cd globetrotter-bff
docker-compose down

echo "🛑 Stopping globetrotter-mcp-server..."
cd ../globetrotter-mcp-server
docker-compose down

echo "🛑 Stopping globetrotter-services..."
cd ../globetrotter-services
docker-compose down

echo "🛑 Stopping logging infrastructure..."
cd ../globetrotter-logging
docker-compose down

echo "🧹 Cleaning up..."
cd ../globetrotter-bff

# Remove the shared network if no containers are using it
if docker network ls | grep -q globetrotter-network; then
    echo "🌐 Removing shared network..."
    docker network rm globetrotter-network 2>/dev/null || echo "Network still in use by other containers"
fi

echo "✅ All services stopped successfully!"
