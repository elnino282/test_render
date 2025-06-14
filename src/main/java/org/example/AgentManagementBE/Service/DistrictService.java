package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.District;
import org.example.AgentManagementBE.Repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DistrictService {
    private final DistrictRepository districtRepository;
    
    @Autowired
    public DistrictService(DistrictRepository districtRepository) {
        this.districtRepository = districtRepository;
    }
    
    public ResponseEntity<Map<String, Object>> getAllDistrict() {
        Map<String, Object> response = new HashMap<>();
        try {
            Iterable<District> districts = districtRepository.findAll();
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy danh sách quận thành công");
            response.put("data", districts);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy danh sách quận: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> addDistrict(District district) {
        Map<String, Object> response = new HashMap<>();
        try {
            District existingDistrict = districtRepository.findByDistrictName(district.getDistrictName());
            if (existingDistrict != null) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Quận đã tồn tại!");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            
            District savedDistrict = districtRepository.save(district);
            response.put("code", 201);
            response.put("status", "success");
            response.put("message", "Thêm quận thành công!");
            response.put("data", savedDistrict);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi thêm quận: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> getDistrictByName(String districtName) {
        Map<String, Object> response = new HashMap<>();
        try {
            District district = districtRepository.findByDistrictName(districtName);
            if (district == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy quận!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }
            
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Lấy thông tin quận thành công");
            response.put("data", district);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi lấy thông tin quận: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> updateDistrict(String oldDistrictName, District updatedDistrict) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Kiểm tra quận cũ có tồn tại không
            District existingDistrict = districtRepository.findByDistrictName(oldDistrictName);
            if (existingDistrict == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy quận cần cập nhật!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            // Kiểm tra tên mới có bị trùng không
            if (!oldDistrictName.equals(updatedDistrict.getDistrictName())) {
                District checkNewName = districtRepository.findByDistrictName(updatedDistrict.getDistrictName());
                if (checkNewName != null) {
                    response.put("code", 400);
                    response.put("status", "error");
                    response.put("message", "Tên quận mới đã tồn tại!");
                    response.put("data", null);
                    return ResponseEntity.badRequest().body(response);
                }
            }

            // Cập nhật thông tin
            existingDistrict.setDistrictName(updatedDistrict.getDistrictName());
            District savedDistrict = districtRepository.save(existingDistrict);
            
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Cập nhật quận thành công");
            response.put("data", savedDistrict);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi cập nhật quận: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> deleteDistrict(String districtName) {
        Map<String, Object> response = new HashMap<>();
        try {
            District existingDistrict = districtRepository.findByDistrictName(districtName);
            if (existingDistrict == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy quận!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            }

            districtRepository.delete(existingDistrict);
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Xóa quận thành công");
            response.put("data", null);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("status", "error");
            response.put("message", "Lỗi khi xóa quận: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(500).body(response);
        }
    }
}
