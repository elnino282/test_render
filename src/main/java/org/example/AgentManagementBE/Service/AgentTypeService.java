package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.Agent;
import org.example.AgentManagementBE.Model.AgentType;
import org.example.AgentManagementBE.Repository.AgentRepository;
import org.example.AgentManagementBE.Repository.AgentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgentTypeService {
    private final AgentTypeRepository agentTypeRepository;
    private final AgentRepository agentRepository;
    
    @Autowired
    public AgentTypeService(AgentTypeRepository agentTypeRepository, AgentRepository agentRepository) {
        this.agentTypeRepository = agentTypeRepository;
        this.agentRepository = agentRepository;
    }

    public ResponseEntity<Map<String, Object>> getAgentTypeRepository() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<AgentType> agentTypeList = (List<AgentType>) agentTypeRepository.findAll();
            if (!agentTypeList.isEmpty()) {
                response.put("code", 200);
                response.put("status", "success");
                response.put("message", "Lấy danh sách loại đại lý thành công");
                response.put("data", agentTypeList);
                return ResponseEntity.ok(response);
            } else {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy loại đại lý nào!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy danh sách loại đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> addAgentType(AgentType newAgentType) {
        Map<String, Object> response = new HashMap<>();
        try {
            AgentType existingAgentType = agentTypeRepository.findByAgentTypeName(newAgentType.getAgentTypeName());
            if (existingAgentType != null) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Loại đại lý đã tồn tại!");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }   
            AgentType savedAgentType = agentTypeRepository.save(newAgentType);
            response.put("code", 201);
            response.put("status", "success");
            response.put("message", "Thêm loại đại lý thành công!");
            response.put("data", savedAgentType);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi thêm loại đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> updateAgentType(AgentType newAgentType) {
        Map<String, Object> response = new HashMap<>();
        try {
            AgentType existingAgentType = agentTypeRepository.findByAgentTypeName(newAgentType.getAgentTypeName());
            if (existingAgentType == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy loại đại lý!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            List<Agent> existingAgent = agentRepository.getAgentDebt(existingAgentType.getAgentTypeID());

            for (Agent agent : existingAgent) {
                if (agent.getDebtMoney() > newAgentType.getMaximumDebt() && agent.getAgentTypeID().getAgentTypeID() == existingAgentType.getAgentTypeID()) {
                    response.put("code", 400);
                    response.put("status", "error");
                    response.put("message", "Không thể cập nhật vì có đại lý đang nợ vượt quá số tiền nợ tối đa mới!");
                    response.put("data", null);
                    return ResponseEntity.badRequest().body(response);
                }
            }

            existingAgentType.setMaximumDebt(newAgentType.getMaximumDebt());
            AgentType updatedAgentType = agentTypeRepository.save(existingAgentType);
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Cập nhật loại đại lý thành công!");
            response.put("data", updatedAgentType);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi cập nhật loại đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getAgentTypeByName(String agentTypeName) {
        Map<String, Object> response = new HashMap<>();
        try {
            AgentType agentType = agentTypeRepository.findByAgentTypeName(agentTypeName);
            if (agentType == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy loại đại lý!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
            
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy thông tin loại đại lý thành công");
            response.put("data", agentType);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy thông tin loại đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteAgentType(String agentTypeName) {
        Map<String, Object> response = new HashMap<>();
        try {
            AgentType existingAgentType = agentTypeRepository.findByAgentTypeName(agentTypeName);
            if (existingAgentType == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy loại đại lý!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            List<Agent> agents = agentRepository.getAgentDebt(existingAgentType.getAgentTypeID());
            if (!agents.isEmpty()) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Không thể xóa loại đại lý vì đang có đại lý sử dụng!");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }

            agentTypeRepository.delete(existingAgentType);
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Xóa loại đại lý thành công!");
            response.put("data", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi xóa loại đại lý: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}
