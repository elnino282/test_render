package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.District;
import org.example.AgentManagementBE.Service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/district")
public class DistrictController {
    @Autowired
    private final DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping("/getAllDistricts")
    public ResponseEntity<Map<String, Object>> getAllDistricts() {
        return districtService.getAllDistrict();
    }

    @PostMapping("/addDistrict")
    public ResponseEntity<Map<String, Object>> createDistrict(@RequestBody District district) {
        return districtService.addDistrict(district);
    }

    @GetMapping("/getDistrictByName")
    public ResponseEntity<Map<String, Object>> getDistrictByName(@RequestParam String districtName) {
        return districtService.getDistrictByName(districtName);
    }

    @PutMapping("/updateDistrict")
    public ResponseEntity<Map<String, Object>> updateDistrict(
            @RequestParam String oldDistrictName,
            @RequestBody District district) {
        return districtService.updateDistrict(oldDistrictName, district);
    }

    @DeleteMapping("/deleteDistrict")
    public ResponseEntity<Map<String, Object>> deleteDistrict(@RequestParam String districtName) {
        return districtService.deleteDistrict(districtName);
    }
}
