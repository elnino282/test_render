package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.Unit;
import org.example.AgentManagementBE.Repository.UnitRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UnitService {
    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public ResponseEntity<Map<String, Object>> insertUnit(Unit unit) {
        Map<String, Object> response = new HashMap<>();
        try {
            Unit existingUnit = unitRepository.findByUnitName(unit.getUnitName());
            if (existingUnit != null) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Đơn vị đã tồn tại!");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            
            Unit savedUnit = unitRepository.save(unit);
            response.put("code", 201);
            response.put("status", "success");
            response.put("message", "Thêm đơn vị thành công!");
            response.put("data", savedUnit);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi thêm đơn vị: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getAllUnit() {
        Map<String, Object> response = new HashMap<>();
        try {
            Iterable<Unit> units = unitRepository.findAll();
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy danh sách đơn vị thành công");
            response.put("data", units);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy danh sách đơn vị: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getUnitByName(String unitName) {
        Map<String, Object> response = new HashMap<>();
        try {
            Unit unit = unitRepository.findByUnitName(unitName);
            if (unit == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy đơn vị!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
            
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy thông tin đơn vị thành công");
            response.put("data", unit);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy thông tin đơn vị: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> updateUnit(String oldUnitName, Unit updatedUnit) {
        Map<String, Object> response = new HashMap<>();
        try {
            Unit existingUnit = unitRepository.findByUnitName(oldUnitName);
            if (existingUnit == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy đơn vị cần cập nhật!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            // Kiểm tra tên mới có bị trùng không
            if (!oldUnitName.equals(updatedUnit.getUnitName())) {
                Unit checkNewName = unitRepository.findByUnitName(updatedUnit.getUnitName());
                if (checkNewName != null) {
                    response.put("code", 400);
                    response.put("status", "error");
                    response.put("message", "Tên đơn vị mới đã tồn tại!");
                    response.put("data", null);
                    return ResponseEntity.badRequest().body(response);
                }
            }

            existingUnit.setUnitName(updatedUnit.getUnitName());
            Unit savedUnit = unitRepository.save(existingUnit);
            
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Cập nhật đơn vị thành công");
            response.put("data", savedUnit);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi cập nhật đơn vị: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteUnit(String unitName) {
        Map<String, Object> response = new HashMap<>();
        try {
            Unit existingUnit = unitRepository.findByUnitName(unitName);
            if (existingUnit == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy đơn vị!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            unitRepository.delete(existingUnit);
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Xóa đơn vị thành công");
            response.put("data", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi xóa đơn vị: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}
