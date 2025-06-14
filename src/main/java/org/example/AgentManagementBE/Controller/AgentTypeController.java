package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.AgentType;
import org.example.AgentManagementBE.Service.AgentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/agentType")
public class AgentTypeController {
    @Autowired
    private final AgentTypeService agentTypeService;
    
    public AgentTypeController(AgentTypeService agentTypeService) {
        this.agentTypeService = agentTypeService;
    }

    @GetMapping("/getAllAgentTypes")
    public ResponseEntity<Map<String, Object>> getAllAgentTypes() {
        return agentTypeService.getAgentTypeRepository();
    }

    @PostMapping("/addAgentType")
    public ResponseEntity<Map<String, Object>> createAgentType(@RequestBody AgentType newAgentType) {
        return agentTypeService.addAgentType(newAgentType);
    }

    @PutMapping("/updateAgentType")
    public ResponseEntity<Map<String, Object>> updateAgentType(@RequestBody AgentType newAgentType) {
        return agentTypeService.updateAgentType(newAgentType);
    }

    @GetMapping("/getAgentTypeByName")
    public ResponseEntity<Map<String, Object>> getAgentTypeByName(@RequestParam String agentTypeName) {
        return agentTypeService.getAgentTypeByName(agentTypeName);
    }

    @DeleteMapping("/deleteAgentType")
    public ResponseEntity<Map<String, Object>> deleteAgentType(@RequestParam String agentTypeName) {
        return agentTypeService.deleteAgentType(agentTypeName);
    }
}