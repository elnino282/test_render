package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.Admin;
import org.example.AgentManagementBE.Repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin checkAdmin(String userEmail, String password) {
        try {
            if (userEmail == null || userEmail.trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }
            if (password == null || password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }

            Admin admin = adminRepository.findByUserEmail(userEmail);
            if (admin != null && admin.getPassword().equals(password)) {
                return admin;
            }
            return null;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error checking admin credentials: " + e.getMessage());
        }
    }

    public Admin addNewAdmin(Admin admin) {
        try {
            if (admin == null) {
                throw new IllegalArgumentException("Admin data cannot be null");
            }

            if (admin.getUserEmail() == null || admin.getUserEmail().trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }

            if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty");
            }

            // Check if admin with same email already exists
            Admin existingAdmin = adminRepository.findByUserEmail(admin.getUserEmail());
            if (existingAdmin != null) {
                throw new IllegalArgumentException("Admin with this email already exists");
            }

            return adminRepository.save(admin);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error adding new admin: " + e.getMessage());
        }
    }
}
