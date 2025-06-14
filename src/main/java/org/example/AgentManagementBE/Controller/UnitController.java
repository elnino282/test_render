package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.Unit;
import org.example.AgentManagementBE.Service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/unit")
public class UnitController {
    @Autowired
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping("/addUnit")
    public ResponseEntity<Map<String, Object>> createUnit(@RequestBody Unit unit) {
        return unitService.insertUnit(unit);
    }

    @GetMapping("/getAllUnits")
    public ResponseEntity<Map<String, Object>> getAllUnits() {
        return unitService.getAllUnit();
    }

    @GetMapping("/getUnitByName")
    public ResponseEntity<Map<String, Object>> getUnitByName(@RequestParam String unitName) {
        return unitService.getUnitByName(unitName);
    }

    @PutMapping("/updateUnit")
    public ResponseEntity<Map<String, Object>> updateUnit(
            @RequestParam String oldUnitName,
            @RequestBody Unit unit) {
        return unitService.updateUnit(oldUnitName, unit);
    }

    @DeleteMapping("/deleteUnit")
    public ResponseEntity<Map<String, Object>> deleteUnit(@RequestParam String unitName) {
        return unitService.deleteUnit(unitName);
    }
}
