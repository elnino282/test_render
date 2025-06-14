package org.example.AgentManagementBE.Controller;

import org.example.AgentManagementBE.Model.Person;
import org.example.AgentManagementBE.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/user") 
public class PersonController {
    @Autowired
    private final PersonService personService;
    
    public PersonController(PersonService personService) {
        this.personService = personService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createPerson(@RequestBody Person newPerson) {
        return personService.createPerson(newPerson);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> getUserByEmail(@RequestBody Map<String, String> loginRequest) {
        String personEmail = loginRequest.get("personEmail");
        String personPassword = loginRequest.get("personPassword");
        return personService.getUserByEmail(personEmail, personPassword);
    }
}
