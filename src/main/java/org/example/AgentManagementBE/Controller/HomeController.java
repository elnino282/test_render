package org.example.AgentManagementBE.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "Agent Management Backend API");
        response.put("version", "0.0.1-SNAPSHOT");
        response.put("description", "Software engineering SE104.P21 (back-end)");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "UP");
        
        // API endpoints information
        Map<String, Object> endpoints = new HashMap<>();
        endpoints.put("health", "/actuator/health");
        endpoints.put("info", "/actuator/info");
        endpoints.put("custom_health", "/api/health");
        endpoints.put("ping", "/api/ping");
        
        Map<String, Object> apiEndpoints = new HashMap<>();
        apiEndpoints.put("agents", "/agent/*");
        apiEndpoints.put("users", "/user/*");
        apiEndpoints.put("agent_types", "/agentType/*");
        apiEndpoints.put("districts", "/district/*");
        apiEndpoints.put("units", "/unit/*");
        apiEndpoints.put("products", "/product/*");
        apiEndpoints.put("import_receipts", "/importReceipt/*");
        apiEndpoints.put("export_receipts", "/exportReceipt/*");
        apiEndpoints.put("payment_receipts", "/paymentReceipt/*");
        apiEndpoints.put("sales_reports", "/salesReport/*");
        apiEndpoints.put("debt_reports", "/debtReport/*");
        
        response.put("system_endpoints", endpoints);
        response.put("api_endpoints", apiEndpoints);
        response.put("documentation", "Use GET requests to explore available endpoints");
        
        return ResponseEntity.ok(response);
    }
} 