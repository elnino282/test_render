package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.Admin;
import org.example.AgentManagementBE.Service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;
    
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    
    @PostMapping("/adminLogin")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            String userEmail = loginRequest.get("userEmail");
            String password = loginRequest.get("password");

            if (userEmail == null || userEmail.trim().isEmpty()) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Email không được để trống");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (password == null || password.trim().isEmpty()) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Mật khẩu không được để trống");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            Admin admin = adminService.checkAdmin(userEmail, password);
            if (admin != null) {
                response.put("code", 200);
                response.put("status", "success");
                response.put("message", "Đăng nhập thành công");
                response.put("data", admin);
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 401);
                response.put("status", "error");
                response.put("message", "Email hoặc mật khẩu không đúng");
                response.put("data", null);
                return ResponseEntity.status(401).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi đăng nhập: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
    
    @PostMapping("/addAdmin")
    public ResponseEntity<Map<String, Object>> createAdmin(@RequestBody Admin admin) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (admin == null) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Dữ liệu admin không được để trống");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            if (admin.getUserEmail() == null || admin.getUserEmail().trim().isEmpty()) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Email không được để trống");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Mật khẩu không được để trống");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            Admin createdAdmin = adminService.addNewAdmin(admin);
            if (createdAdmin != null) {
                response.put("code", 201);
                response.put("status", "success");
                response.put("message", "Thêm admin thành công");
                response.put("data", createdAdmin);
                return ResponseEntity.status(201).body(response);
            } else {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Không thể thêm admin");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi thêm admin: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}
