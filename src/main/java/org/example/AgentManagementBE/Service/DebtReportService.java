package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.DebtReport;
import org.example.AgentManagementBE.Model.DebtReportID;
import org.example.AgentManagementBE.Model.Agent;
import org.example.AgentManagementBE.Repository.DebtReportRepository;
import org.example.AgentManagementBE.Repository.AgentRepository;
import org.example.AgentManagementBE.Repository.ExportReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Service
public class DebtReportService {
    private final DebtReportRepository debtReportRepository;
    private final AgentRepository agentRepository;
    private final ExportReceiptRepository exportReceiptRepository;

    @Autowired
    public DebtReportService(DebtReportRepository debtReportRepository,
                         AgentRepository agentRepository, 
                         ExportReceiptRepository exportReceiptRepository) {
        this.debtReportRepository = debtReportRepository;
        this.agentRepository = agentRepository;
        this.exportReceiptRepository = exportReceiptRepository;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void autoCreateDebtReport() {
        List<Agent> agentList = agentRepository.getAllAgent();
        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear = LocalDate.now().getYear();
        List<DebtReport> debtReportList = new ArrayList<>();
        for (Agent agent : agentList) {
            DebtReport debtReport = new DebtReport(
                    new DebtReportID(currentMonth, currentYear, agent),
                    0,
                    0,
                    0);

            debtReportList.add(debtReport);
        }

        debtReportRepository.saveAll(debtReportList);
    }

    public List<Map<String, Object>> getDebtReport(int month, int year) {
        List<Map<String, Object>> debtReportList = new ArrayList<>();
        List<Agent> agentList = agentRepository.getAllAgent();
        for (Agent agent : agentList) { 
            DebtReport debtReport = debtReportRepository.getDebtReportByAgent(month, year, agent.getAgentID());
            if (debtReport == null) {
                debtReport = new DebtReport(
                        new DebtReportID(month, year, agent),
                        0,
                        0,
                        0);
            } else {
                updateFirstDebt(debtReport);
            }
            
            Map<String, Object> reportData = new HashMap<>();
            reportData.put("month", debtReport.getDebtReportID().getMonth());
            reportData.put("year", debtReport.getDebtReportID().getYear());
            
            Map<String, Object> agentInfo = new HashMap<>();
            agentInfo.put("agentId", agent.getAgentID());
            agentInfo.put("agentName", agent.getAgentName());
            reportData.put("agentId", agentInfo);
            
            reportData.put("firstDebt", debtReport.getFirstDebt());
            reportData.put("arisenDebt", debtReport.getArisenDebt());
            reportData.put("lastDebt", debtReport.getFirstDebt() + debtReport.getArisenDebt());
            
            debtReportList.add(reportData);
        }
        return debtReportList;
    }

    public void updateFirstDebt(DebtReport debtReport) {
        DebtReport existingDebtReport = debtReportRepository.getDebtReportByAgent(debtReport.getDebtReportID().getMonth() - 1, debtReport.getDebtReportID().getYear(), debtReport.getDebtReportID().getAgentID().getAgentID());
        Integer firstDebt = 0;
        if (existingDebtReport != null) {
            firstDebt = existingDebtReport.getFirstDebt() + existingDebtReport.getArisenDebt();
        }
        debtReport.setFirstDebt(firstDebt);
    }

    public DebtReport createDebtReport(DebtReport debtReport) {
        // Calculate lastDebt before saving
        debtReport.setLastDebt(debtReport.getFirstDebt() + debtReport.getArisenDebt());
        return debtReportRepository.save(debtReport);
    }

    public String getAgentNameById(int agentId) {
        Agent agent = agentRepository.getAgentById(agentId);
        return agent != null ? agent.getAgentName() : null;
    }
}