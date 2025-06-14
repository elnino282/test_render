# PowerShell script to start Spring Boot application
Write-Host "=== Agent Management Backend - Windows Launcher ===" -ForegroundColor Cyan

# Set environment variable for production profile
Write-Host "Setting production profile..." -ForegroundColor Yellow
$env:SPRING_PROFILES_ACTIVE = "prod"
Write-Host "Environment variable set: SPRING_PROFILES_ACTIVE = $env:SPRING_PROFILES_ACTIVE" -ForegroundColor Green

# Build application
Write-Host "Building application..." -ForegroundColor Yellow
& ./mvnw.cmd clean package -DskipTests

if ($LASTEXITCODE -ne 0) {
    Write-Host "Build failed!" -ForegroundColor Red
    Read-Host "Press Enter to exit..."
    exit 1
}

Write-Host "Build successful!" -ForegroundColor Green

# Start application (using environment variable instead of JVM argument)
Write-Host "Starting application with production profile..." -ForegroundColor Cyan
Write-Host "Using environment variable method (Windows compatible)..." -ForegroundColor Magenta

# Run Java with proper argument escaping for Windows
& java -jar target/AgentManagementBE-0.0.1-SNAPSHOT.jar

Write-Host "Application stopped." -ForegroundColor Yellow
Read-Host "Press Enter to exit..." 