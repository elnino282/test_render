@echo off
echo Starting Spring Boot application on Windows...
echo Setting production profile...
set SPRING_PROFILES_ACTIVE=prod

echo Building application...
call mvnw.cmd clean package -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo Build failed!
    pause
    exit /b 1
)

echo Starting application with production profile...
echo Using environment variable method for Windows compatibility...
java -jar target/AgentManagementBE-0.0.1-SNAPSHOT.jar

pause 