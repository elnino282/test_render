package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.Agent;
import org.example.AgentManagementBE.Service.AgentService;
import org.example.AgentManagementBE.TriggerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/agent")
public class AgentController {
    @Autowired
    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @GetMapping("/getAllAgents")
    public ResponseEntity<Map<String, Object>> getAllAgents() {
        return agentService.getAllAgent();
    }

    @PostMapping("/addAgent")
    public ResponseEntity<Map<String, Object>> createAgent(@RequestBody Agent agent) {
        return agentService.insertAgent(agent);
    }

    @GetMapping("/getAgentById")
    public ResponseEntity<Map<String, Object>> getAgentById(@RequestParam int agentId) {
        return agentService.getAgentById(agentId);
    }

    @GetMapping("/getAgentByName")
    public ResponseEntity<Map<String, Object>> getAgentByName(@RequestParam String agentName) {
        return agentService.getAgentByName(agentName);
    }

    @PutMapping("/updateDebt")
    public ResponseEntity<Map<String, Object>> updateDebt(@RequestBody Map<String, Integer> request) {
        int agentId = request.get("agentId");
        int debtMoney = request.get("debtMoney");
        return agentService.updateDebtBook(debtMoney, agentId);
    }

    @DeleteMapping("/deleteAgent")
    public ResponseEntity<Map<String, Object>> deleteAgent(@RequestParam int agentId) throws TriggerException {
        return agentService.deleteAgent(agentId);
    }
}