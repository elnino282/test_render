package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.DebtReport;
import org.example.AgentManagementBE.Service.DebtReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/debtReport")
public class DebtReportController {
    @Autowired
    private final DebtReportService debtReportService;

    public DebtReportController(DebtReportService debtReportService) {
        this.debtReportService = debtReportService;
    }

    @GetMapping("/getDebtReport")
    public ResponseEntity<Map<String, Object>> getDebtReport(
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

            List<Map<String, Object>> debtReportList = debtReportService.getDebtReport(month, year);
            if (!debtReportList.isEmpty()) {
                response.put("code", 200);
                response.put("status", "success");
                response.put("message", "Lấy báo cáo công nợ thành công");
                response.put("data", debtReportList);
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy báo cáo công nợ");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/updateDebtReport")
    public ResponseEntity<Map<String, Object>> createDebtReport(@RequestBody DebtReport debtReport) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (debtReport == null) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Dữ liệu báo cáo công nợ không được để trống");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            DebtReport createdReport = debtReportService.createDebtReport(debtReport);
            Map<String, Object> reportData = new HashMap<>();
            reportData.put("month", createdReport.getDebtReportID().getMonth());
            reportData.put("year", createdReport.getDebtReportID().getYear());
            
            Map<String, Object> agentInfo = new HashMap<>();
            agentInfo.put("agentId", createdReport.getDebtReportID().getAgentID().getAgentID());
            agentInfo.put("agentName", debtReportService.getAgentNameById(createdReport.getDebtReportID().getAgentID().getAgentID()));
            reportData.put("agentId", agentInfo);
            
            reportData.put("firstDebt", createdReport.getFirstDebt());
            reportData.put("arisenDebt", createdReport.getArisenDebt());
            reportData.put("lastDebt", createdReport.getFirstDebt() + createdReport.getArisenDebt());

            response.put("code", 201);
            response.put("status", "success");
            response.put("message", "Cập nhật báo cáo công nợ thành công");
            response.put("data", reportData);
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