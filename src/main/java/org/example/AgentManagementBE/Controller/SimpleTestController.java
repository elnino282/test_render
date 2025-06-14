package org.example.AgentManagementBE.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/simple")
public class SimpleTestController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Simple Test Controller!";
    }
    
    @GetMapping("/status")
    public String status() {
        return "Application is running successfully!";
    }
} 