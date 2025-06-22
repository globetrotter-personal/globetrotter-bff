#!/bin/bash

# Globetrotter Services Startup Script

echo "🚀 Starting Globetrotter Services..."

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
docker-compose down

# Build and start services
echo "🔨 Building and starting services..."
docker-compose up -d --build

# Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 30

# Check service status
echo "📊 Service Status:"
docker-compose ps

# Test the BFF endpoint
echo "🧪 Testing BFF endpoint..."
sleep 10
curl -s -X GET "http://localhost:8084/api/travel/flights?customerSearchQuery=I%20want%20to%20fly%20from%20LAX%20to%20JFK%20on%20January%2015th%202025%20with%201%20passenger%20in%20economy%20class" | head -c 200

echo ""
echo "✅ Services started successfully!"
echo ""
echo "🌐 Available endpoints:"
echo "   BFF API: http://localhost:8084/api/travel/flights"
echo "   Services API: http://localhost:8080/travel/flights/search"
echo "   MCP Server: http://localhost:8082"
echo ""
echo "📝 View logs: docker-compose logs -f"
echo "🛑 Stop services: docker-compose down"
