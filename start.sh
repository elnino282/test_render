#!/bin/bash
echo "Starting Spring Boot application..."
export SPRING_PROFILES_ACTIVE=prod
echo "Profile set to: $SPRING_PROFILES_ACTIVE"
java -jar target/AgentManagementBE-0.0.1-SNAPSHOT.jar 