package org.example.AgentManagementBE.Repository;

import org.example.AgentManagementBE.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AdminRepository extends JpaRepository<Admin, String> {
    Admin findByUserEmail(String userEmail);
}
