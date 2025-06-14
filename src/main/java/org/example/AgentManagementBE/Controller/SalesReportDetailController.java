package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.SalesReportDetail;
import org.example.AgentManagementBE.Service.SalesReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/salesReportDetail")
public class SalesReportDetailController {
    private final SalesReportDetailService salesReportDetailService;

    @Autowired
    public SalesReportDetailController(SalesReportDetailService salesReportDetailService) {
        this.salesReportDetailService = salesReportDetailService;
    }

    @GetMapping("/getSalesReportDetailByAgentId")
    public ResponseEntity<Map<String, Object>> getSalesReportDetailByAgentId(@RequestParam int agentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Map<String, Object>> details = salesReportDetailService.getSalesReportDetailByAgentId(agentId);
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy chi tiết báo cáo theo agent thành công");
            response.put("data", details);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getSalesReportDetailByAgentIdAndSalesReportId")
    public ResponseEntity<Map<String, Object>> getSalesReportDetailByAgentIdAndSalesReportId(
            @RequestParam int agentId,
            @RequestParam int salesReportId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> detail = salesReportDetailService.getSalesReportDetailByAgentIdAndSalesReportId(agentId, salesReportId);
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy chi tiết báo cáo thành công");
            response.put("data", detail);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/getSalesReportDetail")
    public ResponseEntity<Map<String, Object>> getSalesReportDetail(
            @RequestParam int month,
            @RequestParam int year) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (month < 1 || month > 12) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Tháng không hợp lệ (phải từ 1-12)");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (year < 2000 || year > 2100) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Năm không hợp lệ");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            List<Map<String, Object>> details = salesReportDetailService.getSalesReportDetail(month, year);
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy chi tiết báo cáo thành công");
            response.put("data", details);
            return ResponseEntity.status(200).body(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/addSalesReportDetail")
    public ResponseEntity<Map<String, Object>> createSalesReportDetail(@RequestBody Map<String, Integer> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            int month = request.get("month");
            int year = request.get("year");

            if (month < 1 || month > 12) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Tháng không hợp lệ (phải từ 1-12)");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (year < 2000 || year > 2100) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Năm không hợp lệ");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            List<Map<String, Object>> details = salesReportDetailService.createSalesReportDetail(month, year);
            response.put("code", 201);
            response.put("status", "success");
            response.put("message", "Tạo chi tiết báo cáo thành công");
            response.put("data", details);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}