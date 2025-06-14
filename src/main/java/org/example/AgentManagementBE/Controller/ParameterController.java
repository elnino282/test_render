package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.Parameter;
import org.example.AgentManagementBE.Service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/parameter")
public class ParameterController {
    @Autowired
    private final ParameterService parameterService;

    public ParameterController(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

    @PostMapping("/addParameter")
    public ResponseEntity<Map<String, Object>> createParameter(@RequestBody Parameter parameter) {
        Map<String, Object> response = new HashMap<>();
        boolean result = parameterService.addParameter(parameter);
        if (result) {
            response.put("code", 201);
            response.put("status", "success");
            response.put("message", "Thêm tham số thành công!");
            response.put("data", parameter);
            return ResponseEntity.status(201).body(response);
        }
        response.put("code", 400);
        response.put("status", "error");
        response.put("message", "Thêm tham số thất bại! Tham số đã tồn tại!");
        response.put("data", null);
        return ResponseEntity.badRequest().body(response);
    }

    @PutMapping("/updateParameter")
    public ResponseEntity<Map<String, Object>> updateParameter(@RequestBody Parameter parameter) {
        Map<String, Object> response = new HashMap<>();
        boolean result = parameterService.updateParameter(parameter);
        if (result) {
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Cập nhật tham số thành công!");
            response.put("data", parameter);
            return ResponseEntity.ok(response);
        }
        response.put("code", 400);
        response.put("status", "error");
        response.put("message", "Cập nhật tham số thất bại!");
        response.put("data", null);
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/getParameterValue")
    public ResponseEntity<Map<String, Object>> getParameter(@RequestParam String parameterName) {
        Map<String, Object> response = new HashMap<>();
        int value = parameterService.getParameter(parameterName);
        if (value != -1) {
            Map<String, Object> data = new HashMap<>();
            data.put("parameterName", parameterName);
            data.put("value", value);
            
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy giá trị tham số thành công");
            response.put("data", data);
            return ResponseEntity.ok(response);
        }
        response.put("code", 404);
        response.put("status", "error");
        response.put("message", "Không tìm thấy tham số!");
        response.put("data", null);
        return ResponseEntity.status(404).body(response);
    }

    @GetMapping("/getAllParameter")
    public ResponseEntity<Map<String, Object>> getAllParameters() {
        Map<String, Object> response = new HashMap<>();
        try {
            Iterable<Parameter> parameters = parameterService.getAllParameter();
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy danh sách tham số thành công");
            response.put("data", parameters);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy danh sách tham số: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}
