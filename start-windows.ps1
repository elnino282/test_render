# PowerShell script to start Spring Boot application
Write-Host "Starting Spring Boot application on Windows..." -ForegroundColor Green
Write-Host "Setting production profile..." -ForegroundColor Yellow

# Set environment variable
$env:SPRING_PROFILES_ACTIVE = "prod"

Write-Host "Building application..." -ForegroundColor Yellow
./mvnw.cmd clean package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed!" -ForegroundColor Red
    Read-Host "Press Enter to exit..."
    exit 1
}

Write-Host "Starting application with production profile..." -ForegroundColor Green
java -Dspring.profiles.active=prod -jar target/AgentManagementBE-0.0.1-SNAPSHOT.jar

Read-Host "Press Enter to exit..." 