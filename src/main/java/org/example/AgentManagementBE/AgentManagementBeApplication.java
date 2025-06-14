package org.example.AgentManagementBE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "org.example.AgentManagementBE")
@EnableJpaAuditing
@EnableScheduling
public class AgentManagementBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentManagementBeApplication.class, args);
    }
}

