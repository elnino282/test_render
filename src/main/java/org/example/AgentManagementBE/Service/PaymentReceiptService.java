package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.DebtReport;
import org.example.AgentManagementBE.Model.Agent;
import org.example.AgentManagementBE.Model.PaymentReceipt;
import org.example.AgentManagementBE.Repository.DebtReportRepository;
import org.example.AgentManagementBE.Repository.AgentRepository;
import org.example.AgentManagementBE.Repository.PaymentReceiptRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

@Service
public class PaymentReceiptService {
    private final PaymentReceiptRepository paymentReceiptRepository;
    private final AgentRepository agentRepository;
    private final AgentService agentService;
    private final DebtReportRepository debtReportRepository;

    public PaymentReceiptService(PaymentReceiptRepository paymentReceiptRepository, AgentRepository agentRepository, AgentService agentService, DebtReportRepository debtReportRepository) {
        this.paymentReceiptRepository = paymentReceiptRepository;
        this.agentRepository = agentRepository;
        this.agentService = agentService;
        this.debtReportRepository = debtReportRepository;
    }

    public Iterable<PaymentReceipt> getAllPaymentReceipt() {
        try {
            return paymentReceiptRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách phiếu thu tiền: " + e.getMessage());
        }
    }

    public Iterable<PaymentReceipt> getPaymentReceiptByID(int paymentReceiptId) {
        try {
            return paymentReceiptRepository.getPaymentReceiptByID(paymentReceiptId);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy phiếu thu tiền theo ID: " + e.getMessage());
        }
    }

    public Iterable<PaymentReceipt> getPaymentReceiptByAgentID(int agentId) {
        try {
            return paymentReceiptRepository.getPaymentReceiptByAgentID(agentId);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy phiếu thu tiền theo đại lý: " + e.getMessage());
        }
    }

    public Map<String, Object> insertPaymentReceipt(PaymentReceipt paymentReceipt) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (paymentReceipt == null) {
                throw new IllegalArgumentException("Dữ liệu phiếu thu tiền không được để trống");
            }

            if (paymentReceipt.getAgentID() == null) {
                throw new IllegalArgumentException("Đại lý không được để trống");
            }

            if (paymentReceipt.getRevenue() <= 0) {
                throw new IllegalArgumentException("Số tiền thu phải lớn hơn 0");
            }

            // Get existing agent from database
            Agent existingAgent = agentRepository.getAgentById(paymentReceipt.getAgentID().getAgentID());
            if (existingAgent == null) {
                throw new IllegalArgumentException("Không tìm thấy đại lý");
            }

            int paymentAmount = paymentReceipt.getRevenue();
            int oldDebtMoney = existingAgent.getDebtMoney();
            
            if (oldDebtMoney < paymentAmount) {
                result.put("success", false);
                return result;
            }

            // Update agent's debt
            existingAgent.setDebtMoney(oldDebtMoney - paymentAmount);
            agentRepository.save(existingAgent);

            // Save payment receipt and get the saved entity with generated ID
            PaymentReceipt savedPaymentReceipt = paymentReceiptRepository.save(paymentReceipt);

            // Update debt report
            Date receiptDate = paymentReceipt.getPaymentDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(receiptDate);
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);

            DebtReport existingDebtReport = debtReportRepository.getDebtReportByAgent(
                month, year, paymentReceipt.getAgentID().getAgentID());
            
            if (existingDebtReport != null) {
                int oldArisenDebt = existingDebtReport.getArisenDebt();
                int newArisenDebt = oldArisenDebt - paymentReceipt.getRevenue();
                existingDebtReport.setArisenDebt(newArisenDebt);
                debtReportRepository.save(existingDebtReport);
            }

            // Create response data in the desired format
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("paymentReceiptID", savedPaymentReceipt.getPaymentReceiptID());
            
            // Format paymentDate to yyyy-MM-dd format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedPaymentDate = dateFormat.format(paymentReceipt.getPaymentDate());
            
            // Create nested agentID object with all agent details plus payment info
            Map<String, Object> agentData = new LinkedHashMap<>();
            agentData.put("agentID", existingAgent.getAgentID());
            agentData.put("agentName", existingAgent.getAgentName());
            agentData.put("address", existingAgent.getAddress());
            agentData.put("phone", existingAgent.getPhone());
            agentData.put("email", existingAgent.getEmail());
            agentData.put("paymentDate", formattedPaymentDate);
            agentData.put("revenue", paymentAmount);
            
            data.put("agentID", agentData);

            result.put("success", true);
            result.put("data", data);
            return result;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo phiếu thu tiền: " + e.getMessage());
        }
    }
}
