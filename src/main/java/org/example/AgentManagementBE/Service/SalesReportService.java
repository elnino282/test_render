package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.SalesReport;
import org.example.AgentManagementBE.Model.ExportReceipt;
import org.example.AgentManagementBE.Repository.SalesReportRepository;
import org.example.AgentManagementBE.Repository.ExportReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
public class SalesReportService {
    private final SalesReportRepository salesReportRepository;
    private final ExportReceiptRepository exportReceiptRepository;

    @Autowired
    public SalesReportService(SalesReportRepository salesReportRepository, ExportReceiptRepository exportReceiptRepository) {
        this.salesReportRepository = salesReportRepository;
        this.exportReceiptRepository = exportReceiptRepository;
    }

    @Transactional
    public Map<String, Object> createSalesReport(int month, int year) {
        try {
            List<ExportReceipt> exportReceiptList = exportReceiptRepository.getAllExportReceiptByMonthAndYearOfDateReceipt(month, year);
            if (exportReceiptList == null || exportReceiptList.isEmpty()) {
                throw new RuntimeException("Không tìm thấy phiếu xuất nào trong tháng " + month + "/" + year);
            }

            SalesReport salesReport = new SalesReport(month, year);
            int totalMoney = exportReceiptRepository.getTotalMoneyByMonthAndYear(month, year);
            
            salesReport.setTotalRevenue(totalMoney);
            salesReport = salesReportRepository.save(salesReport);

            Map<String, Object> response = new HashMap<>();
            response.put("code", 201);
            response.put("status", "success");
            response.put("message", "Tạo báo cáo doanh số thành công");

            Map<String, Object> data = new HashMap<>();
            data.put("salesReportID", salesReport.getSalesReportID());
            data.put("month", salesReport.getMonth());
            data.put("year", salesReport.getYear());
            data.put("totalRevenue", salesReport.getTotalRevenue());
            response.put("data", data);

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo báo cáo doanh số: " + e.getMessage());
        }
    }

    @Transactional
    public Map<String, Object> getSalesReportByMonthAndYear(int month, int year) {
        try {
            SalesReport salesReport = salesReportRepository.getSalesReportByMonthAndYear(month, year);
            if (salesReport == null) {
                throw new RuntimeException("Không tìm thấy báo cáo doanh số cho tháng " + month + "/" + year);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy báo cáo doanh số thành công");

            Map<String, Object> data = new HashMap<>();
            data.put("salesReportID", salesReport.getSalesReportID());
            data.put("month", salesReport.getMonth());
            data.put("year", salesReport.getYear());
            data.put("totalRevenue", salesReport.getTotalRevenue());
            response.put("data", data);

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy báo cáo doanh số: " + e.getMessage());
        }
    }
}