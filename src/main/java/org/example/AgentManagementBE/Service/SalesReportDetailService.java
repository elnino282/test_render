package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.SalesReport;
import org.example.AgentManagementBE.Model.SalesReportDetail;
import org.example.AgentManagementBE.Model.Agent;
import org.example.AgentManagementBE.Model.ExportReceipt;
import org.example.AgentManagementBE.Repository.SalesReportRepository;
import org.example.AgentManagementBE.Repository.SalesReportDetailRepository;
import org.example.AgentManagementBE.Repository.AgentRepository;
import org.example.AgentManagementBE.Repository.ExportReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SalesReportDetailService {
    private final SalesReportDetailRepository salesReportDetailRepository;
    private final ExportReceiptRepository exportReceiptRepository;
    private final SalesReportRepository salesReportRepository;
    private final AgentRepository agentRepository;

    @Autowired
    public SalesReportDetailService(SalesReportDetailRepository salesReportDetailRepository,
                         ExportReceiptRepository exportReceiptRepository,
                         SalesReportRepository salesReportRepository,
                         AgentRepository agentRepository) {
        this.salesReportDetailRepository = salesReportDetailRepository;
        this.exportReceiptRepository = exportReceiptRepository;
        this.salesReportRepository = salesReportRepository;
        this.agentRepository = agentRepository;
    }

    public List<Map<String, Object>> getSalesReportDetailByAgentId(int agentID) {
        try {
            List<SalesReportDetail> details = salesReportDetailRepository.getSalesReportDetailByAgentID(agentID);
            List<Map<String, Object>> simplifiedList = new ArrayList<>();
            
            if (details == null || details.isEmpty()) {
                return simplifiedList; // Trả về list rỗng nếu không tìm thấy dữ liệu
            }

            for (SalesReportDetail detail : details) {
                Map<String, Object> simplifiedDetail = new HashMap<>();
                
                Map<String, Object> agentInfo = new HashMap<>();
                agentInfo.put("agentID", detail.getAgentID().getAgentID());
                agentInfo.put("agentName", detail.getAgentID().getAgentName());
                simplifiedDetail.put("agentID", agentInfo);

                Map<String, Object> salesReportInfo = new HashMap<>();
                salesReportInfo.put("salesReportID", detail.getSalesReportID().getSalesReportID());
                salesReportInfo.put("month", detail.getSalesReportID().getMonth());
                salesReportInfo.put("year", detail.getSalesReportID().getYear());
                salesReportInfo.put("totalRevenue", detail.getSalesReportID().getTotalRevenue());
                simplifiedDetail.put("salesReportID", salesReportInfo);

                simplifiedDetail.put("quantityExport", detail.getQuantityExport());
                simplifiedDetail.put("totalValue", detail.getTotalValue());
                simplifiedDetail.put("proportion", detail.getProportion());

                simplifiedList.add(simplifiedDetail);
            }
            return simplifiedList;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy chi tiết báo cáo theo agent: " + e.getMessage());
        }
    }

    public Map<String, Object> getSalesReportDetailByAgentIdAndSalesReportId(int agentID, int salesReportID) {
        try {
            SalesReportDetail detail = salesReportDetailRepository.getSalesReportDetailByAgentIDAndSalesReportID(agentID, salesReportID);
            if (detail == null) {
                return new HashMap<>(); // Trả về map rỗng nếu không tìm thấy dữ liệu
            }

            Map<String, Object> simplifiedDetail = new HashMap<>();
            
            Map<String, Object> agentInfo = new HashMap<>();
            agentInfo.put("agentID", detail.getAgentID().getAgentID());
            agentInfo.put("agentName", detail.getAgentID().getAgentName());
            simplifiedDetail.put("agentID", agentInfo);

            Map<String, Object> salesReportInfo = new HashMap<>();
            salesReportInfo.put("salesReportID", detail.getSalesReportID().getSalesReportID());
            salesReportInfo.put("month", detail.getSalesReportID().getMonth());
            salesReportInfo.put("year", detail.getSalesReportID().getYear());
            salesReportInfo.put("totalRevenue", detail.getSalesReportID().getTotalRevenue());
            simplifiedDetail.put("salesReportID", salesReportInfo);

            simplifiedDetail.put("quantityExport", detail.getQuantityExport());
            simplifiedDetail.put("totalValue", detail.getTotalValue());
            simplifiedDetail.put("proportion", detail.getProportion());

            return simplifiedDetail;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy chi tiết báo cáo: " + e.getMessage());
        }
    }

    @Transactional
    public List<Map<String, Object>> createSalesReportDetail(int month, int year) {
        try {
            SalesReport salesReport = salesReportRepository.getSalesReportByMonthAndYear(month, year);
            if (salesReport == null) {
                throw new RuntimeException("Không tìm thấy báo cáo doanh số cho tháng " + month + "/" + year);
            }

            List<Agent> agentList = agentRepository.getAllAgentIdAndName();
            if (agentList == null || agentList.isEmpty()) {
                throw new RuntimeException("Không tìm thấy agent nào");
            }

            int totalMoney = exportReceiptRepository.getTotalMoneyByMonthAndYear(month, year);
            salesReport.setTotalRevenue(totalMoney);
            salesReportRepository.save(salesReport);

            List<Map<String, Object>> salesReportDetailList = new ArrayList<>();

            for (Agent agent : agentList) {
                Integer sum1 = exportReceiptRepository.getTotalMoneyByMonthAndYearOfAgent(agent.getAgentID(), month, year);
                if (sum1 == null) {
                    sum1 = 0;
                }
                double proportion = totalMoney > 0 ? (double) sum1 / totalMoney * 100 : 0;
                
                SalesReportDetail salesReportDetail = new SalesReportDetail(
                    agent,
                    salesReport,
                    exportReceiptRepository.getQuantityExportByMonthAndYearOrderByAgentID(month, year, agent.getAgentID()),
                    sum1,
                    proportion
                );
                salesReportDetailRepository.save(salesReportDetail);

                Map<String, Object> detail = new HashMap<>();
                Map<String, Object> agentInfo = new HashMap<>();
                agentInfo.put("agentID", agent.getAgentID());
                agentInfo.put("agentName", agent.getAgentName());
                detail.put("agentID", agentInfo);

                Map<String, Object> salesReportInfo = new HashMap<>();
                salesReportInfo.put("salesReportID", salesReport.getSalesReportID());
                salesReportInfo.put("month", salesReport.getMonth());
                salesReportInfo.put("year", salesReport.getYear());
                salesReportInfo.put("totalRevenue", totalMoney);
                detail.put("salesReportID", salesReportInfo);

                detail.put("quantityExport", salesReportDetail.getQuantityExport());
                detail.put("totalValue", salesReportDetail.getTotalValue());
                detail.put("proportion", Math.round(proportion * 100.0) / 100.0);

                salesReportDetailList.add(detail);
            }
            return salesReportDetailList;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo chi tiết báo cáo: " + e.getMessage());
        }
    }

    public List<Map<String, Object>> getSalesReportDetail(int month, int year) {
        try {
            SalesReport salesReport = salesReportRepository.getSalesReportByMonthAndYear(month, year);
            if (salesReport == null) {
                throw new RuntimeException("Không tìm thấy báo cáo doanh số cho tháng " + month + "/" + year);
            }
            
            List<Agent> agentList = agentRepository.getAllAgent();
            if (agentList == null || agentList.isEmpty()) {
                throw new RuntimeException("Không tìm thấy agent nào");
            }
            
            List<Map<String, Object>> simplifiedList = new ArrayList<>();
            int totalMoney = exportReceiptRepository.getTotalMoneyByMonthAndYear(month, year);
            
            for (Agent agent : agentList) {
                Integer sum1 = exportReceiptRepository.getTotalMoneyByMonthAndYearOfAgent(agent.getAgentID(), month, year);
                if (sum1 == null) {
                    sum1 = 0;
                }
                double proportion = totalMoney > 0 ? (double) sum1 / totalMoney * 100 : 0;
                proportion = Math.round(proportion * 100.0) / 100.0;
                
                SalesReportDetail salesReportDetail = new SalesReportDetail(
                    agent,
                    salesReport,
                    exportReceiptRepository.getQuantityExportByMonthAndYearOrderByAgentID(month, year, agent.getAgentID()),
                    sum1,
                    proportion
                );
                salesReportDetailRepository.save(salesReportDetail);

                // Create simplified response
                Map<String, Object> detail = new HashMap<>();
                Map<String, Object> agentInfo = new HashMap<>();
                agentInfo.put("agentID", agent.getAgentID());
                agentInfo.put("agentName", agent.getAgentName());
                detail.put("agentID", agentInfo);

                Map<String, Object> salesReportInfo = new HashMap<>();
                salesReportInfo.put("salesReportID", salesReport.getSalesReportID());
                salesReportInfo.put("month", salesReport.getMonth());
                salesReportInfo.put("year", salesReport.getYear());
                salesReportInfo.put("totalRevenue", salesReport.getTotalRevenue());
                detail.put("salesReportID", salesReportInfo);

                detail.put("quantityExport", salesReportDetail.getQuantityExport());
                detail.put("totalValue", salesReportDetail.getTotalValue());
                detail.put("proportion", proportion);

                simplifiedList.add(detail);
            }
            
            return simplifiedList;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy chi tiết báo cáo: " + e.getMessage());
        }
    }
}