#!/bin/bash

echo "Building and deploying Insurance Project..."

# Build backend
echo "Building backend..."
git checkout BackEnd
docker build -t insurance-backend .

# Build frontend  
echo "Building frontend..."
git checkout FrontendNew
docker build -t insurance-frontend .

# Deploy with docker-compose
echo "Starting services..."
docker-compose up -d

echo "Deployment complete!"
echo "Frontend: http://localhost:3000"
echo "Backend: http://localhost:8080"
echo "MySQL: localhost:3306"
