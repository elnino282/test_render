package org.example.AgentManagementBE.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        
        if (statusCode == null) {
            statusCode = 500;
        }
        
        response.put("timestamp", LocalDateTime.now());
        response.put("status", statusCode);
        response.put("error", HttpStatus.valueOf(statusCode).getReasonPhrase());
        response.put("path", requestUri);
        response.put("service", "Agent Management Backend API");
        
        if (statusCode == 404) {
            response.put("message", "The requested endpoint was not found. Please check the API documentation at /");
            response.put("available_endpoints", Map.of(
                "home", "/",
                "test", "/test",
                "health", "/api/health",
                "actuator_health", "/actuator/health"
            ));
        } else {
            response.put("message", "An error occurred while processing your request");
        }
        
        return ResponseEntity.status(statusCode).body(response);
    }
} 