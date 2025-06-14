package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.*;
import org.example.AgentManagementBE.Repository.*;
import org.example.AgentManagementBE.TriggerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgentService {
    private final AgentRepository agentRepository;
    private final AgentTypeRepository agentTypeRepository;
    private final DistrictRepository districtRepository;
    private final ParameterRepository parameterRepository;
    private final DebtReportRepository debtReportRepository;
    private final ExportDetailRepository exportDetailRepository;
    private final ExportReceiptRepository exportReceiptRepository;
    private final PaymentReceiptRepository paymentReceiptRepository;
    private final SalesReportDetailRepository salesReportDetailRepository;

    @Autowired
    public AgentService(AgentRepository agentRepository,
                        AgentTypeRepository agentTypeRepository,
                        DistrictRepository districtRepository,
                        ParameterRepository parameterRepository,
                        DebtReportRepository debtReportRepository,
                        ExportDetailRepository exportDetailRepository,
                        ExportReceiptRepository exportReceiptRepository,
                        PaymentReceiptRepository paymentReceiptRepository,
                        SalesReportDetailRepository salesReportDetailRepository) {
        this.agentRepository = agentRepository;
        this.agentTypeRepository = agentTypeRepository;
        this.districtRepository = districtRepository;
        this.parameterRepository = parameterRepository;
        this.debtReportRepository = debtReportRepository;
        this.exportDetailRepository = exportDetailRepository;
        this.exportReceiptRepository = exportReceiptRepository;
        this.paymentReceiptRepository = paymentReceiptRepository;
        this.salesReportDetailRepository = salesReportDetailRepository;
    }

    private static Calendar calendar;

    public ResponseEntity<Map<String, Object>> getAllAgent() {
        Map<String, Object> response = new HashMap<>();
        try {
            Iterable<Agent> agents = agentRepository.findAll();
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy danh sách đại lý thành công");
            response.put("data", agents);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 400);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy danh sách đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> insertAgent(Agent agent) {
        Map<String, Object> response = new HashMap<>();
        try {
            calendar = Calendar.getInstance();
            District existingDistrict = districtRepository.findByDistrictName(agent.getDistrictID().getDistrictName());
            if (existingDistrict == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy quận!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
            agent.setDistrictID(existingDistrict);

            AgentType existingAgentType = agentTypeRepository.findByAgentTypeName(agent.getAgentTypeID().getAgentTypeName());
            if (existingAgentType == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy loại đại lý!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
            agent.setAgentTypeID(existingAgentType);
            
            Parameter maxAgentParam = parameterRepository.getParameterByName("Số đại lý tối đa trong một quận");
            if (maxAgentParam == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy tham số 'Số đại lý tối đa trong một quận'!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
            
            int n = agentRepository.countAgentByAgentType(agent.getDistrictID().getDistrictNO());
            if (n >= maxAgentParam.getParameterValue()) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Số lượng đại lý trong quận đã đạt tối đa!");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            
            Agent agent1 = agentRepository.save(agent);
            calendar.setTime(agent1.getReceptionDate());
			int month = calendar.get(Calendar.MONTH) + 1;
			int year = calendar.get(Calendar.YEAR);
			DebtReport dr = new DebtReport(new DebtReportID(month, year, agent1), 0, 0, 0);
			debtReportRepository.save(dr);
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Thêm đại lý thành công!");
            response.put("data", agent1);
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            response.put("code", 400);
            response.put("status", "error");
            response.put("message", "Lỗi khi thêm đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getAgentByName(String agentName) {
        Map<String, Object> response = new HashMap<>();
        try {
            Agent agent = agentRepository.getAllAgentByAgentName(agentName);
            if (agent != null) {
                response.put("code", 200);
                response.put("status", "success");
                response.put("message", "Lấy thông tin đại lý thành công");
                response.put("data", agent);
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy đại lý!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 400);
            response.put("status", "error");
            response.put("message", "Lỗi khi tìm đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> updateDebtBook(int debtMoney, int agentID) {
        Map<String, Object> response = new HashMap<>();
        try {
            Agent existingAgent = agentRepository.getAgentById(agentID);
            if (existingAgent == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy đại lý!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            int oldDebtMoney = existingAgent.getDebtMoney();
            int newDebtMoney = oldDebtMoney + debtMoney;
            
            AgentType agentType = agentTypeRepository.findByAgentTypeID(existingAgent.getAgentTypeID().getAgentTypeID());
            if (agentType == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy loại đại lý!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            int maximumDebt = agentType.getMaximumDebt();
            if (maximumDebt < newDebtMoney) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Số tiền nợ mới vượt quá số tiền nợ tối đa cho phép!");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            existingAgent.setDebtMoney(newDebtMoney);
            agentRepository.save(existingAgent);
            
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Cập nhật số tiền nợ thành công!");
            response.put("data", existingAgent);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 400);
            response.put("status", "error");
            response.put("message", "Lỗi khi cập nhật số tiền nợ: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getAgentById(int agentID) {
        Map<String, Object> response = new HashMap<>();
        try {
            Agent agent = agentRepository.getAgentById(agentID);
            if (agent == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy đại lý!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
            
            int maxdebt = agentTypeRepository.findByAgentTypeID(agent.getAgentTypeID().getAgentTypeID()).getMaximumDebt();
            int remainingDebt = maxdebt - agent.getDebtMoney();
            
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy thông tin nợ thành công");
            response.put("data", remainingDebt);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 400);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy thông tin đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Transactional
    public ResponseEntity<Map<String, Object>> deleteAgent(int agentID) throws TriggerException {
        Map<String, Object> response = new HashMap<>();
        try {
            Agent existingAgent = agentRepository.getAgentById(agentID);
            if (existingAgent == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy đại lý!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            if (existingAgent.getDebtMoney() > 0) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Không thể xóa đại lý vì còn nợ tiền!");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            // Xóa các bản ghi liên quan
            List<DebtReport> debtReports = debtReportRepository.getDebtReportByAgent(agentID);
            for (DebtReport debtReport : debtReports) {
                debtReportRepository.delete(debtReport);
            }

            List<ExportDetail> exportDetails = exportDetailRepository.findByAgentID(agentID);
            for (ExportDetail exportDetail : exportDetails) {
                exportDetailRepository.delete(exportDetail);
            }

            List<ExportReceipt> exportReceipts = exportReceiptRepository.findByAgentID(agentID);
            for (ExportReceipt exportReceipt : exportReceipts) {
                exportReceiptRepository.delete(exportReceipt);
            }

            List<PaymentReceipt> paymentReceipts = paymentReceiptRepository.findByAgentID(agentID);
            for (PaymentReceipt paymentReceipt : paymentReceipts) {
                paymentReceiptRepository.delete(paymentReceipt);
            }

            List<SalesReportDetail> salesReportDetails = salesReportDetailRepository.getSalesReportDetailByAgentID(agentID);
            for (SalesReportDetail salesReportDetail : salesReportDetails) {
                salesReportDetailRepository.delete(salesReportDetail);
            }

            agentRepository.delete(existingAgent);
            
            Map<String, Object> data = new HashMap<>();
            data.put("agentID", agentID);
            data.put("agentName", existingAgent.getAgentName());
            data.put("deletedAt", new java.util.Date());
            
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Xóa đại lý thành công!");
            response.put("data", data);
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            Throwable cause = e.getRootCause();
            if (cause instanceof SQLException) {
                SQLException sqlEx = (SQLException) cause;
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Không thể xóa đại lý vì có dữ liệu liên quan: " + sqlEx.getMessage());
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            response.put("code", 400);
            response.put("status", "error");
            response.put("message", "Lỗi khi xóa đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi server khi xóa đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.internalServerError().body(response);
        }
    }
}