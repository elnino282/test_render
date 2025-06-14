package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.Person;
import org.example.AgentManagementBE.Repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public ResponseEntity<Map<String, Object>> getUserByEmail(String personEmail, String personPassword) {
        Map<String, Object> response = new HashMap<>();
        try {
            Person user = personRepository.getUserByEmail(personEmail);
            if(user == null) {
                response.put("code", 404);
                response.put("status", "error");
                response.put("message", "Không tìm thấy người dùng!");
                response.put("data", null);
                return ResponseEntity.status(404).body(response);
            } else if(user.getPersonPassword().equals(personPassword)) {
                response.put("code", 200);
                response.put("status", "success");
                response.put("message", "Đăng nhập thành công!");
                response.put("data", user);
                return ResponseEntity.ok(response);
            }
            response.put("code", 401);
            response.put("status", "error");
            response.put("message", "Mật khẩu không đúng!");
            response.put("data", null);
            return ResponseEntity.status(401).body(response);
        } catch (Exception e) {
            response.put("code", 400);
            response.put("status", "error");
            response.put("message", "Lỗi khi đăng nhập: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<Map<String, Object>> createPerson(Person newPerson) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (existsByEmail(newPerson.getPersonEmail())) {
                response.put("code", 400);
                response.put("status", "error");
                response.put("message", "Email đã tồn tại!");
                response.put("data", null);
                return ResponseEntity.badRequest().body(response);
            }
            
            Person savedPerson = personRepository.save(newPerson);
            response.put("code", 200);
            response.put("status", "success");
            response.put("message", "Tạo người dùng thành công!");
            response.put("data", savedPerson);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 400);
            response.put("status", "error");
            response.put("message", "Lỗi khi tạo người dùng: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.badRequest().body(response);
        }
    }

    public Boolean existsByEmail(String personEmail) {
        return personRepository.existsByEmail(personEmail) != null;
    }
}
