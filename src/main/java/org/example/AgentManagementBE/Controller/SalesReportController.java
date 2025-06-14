package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.SalesReport;
import org.example.AgentManagementBE.Model.SalesReportDetail;
import org.example.AgentManagementBE.Service.SalesReportService;
import org.example.AgentManagementBE.Service.SalesReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/salesReport")
public class SalesReportController {
    @Autowired
    private final SalesReportService salesReportService;

    @Autowired
    private final SalesReportDetailService salesReportDetailService;

    public SalesReportController(SalesReportService salesReportService, SalesReportDetailService salesReportDetailService) {
        this.salesReportService = salesReportService;
        this.salesReportDetailService = salesReportDetailService;
    }

    @GetMapping("/getSalesReportByMonthAndYear")
    public ResponseEntity<Map<String, Object>> getSalesReportByMonthAndYear(
            @RequestParam("month") int month,
            @RequestParam("year") int year) {
        try {
            if (month < 1 || month > 12) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Tháng không hợp lệ (phải từ 1-12)");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (year < 2000 || year > 2100) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Năm không hợp lệ");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            Map<String, Object> response = salesReportService.getSalesReportByMonthAndYear(month, year);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/addSalesReport")
    public ResponseEntity<Map<String, Object>> createSalesReport(@RequestBody Map<String, Integer> request) {
        try {
            int month = request.get("month");
            int year = request.get("year");

            if (month < 1 || month > 12) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Tháng không hợp lệ (phải từ 1-12)");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            if (year < 2000 || year > 2100) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Năm không hợp lệ");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            
            Map<String, Object> response = salesReportService.createSalesReport(month, year);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}