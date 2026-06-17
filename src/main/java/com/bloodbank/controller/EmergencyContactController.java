package com.bloodbank.controller;

import com.bloodbank.model.EmergencyContact;
import com.bloodbank.service.EmergencyContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/emergency-contacts")
@CrossOrigin(origins = "*")
public class EmergencyContactController {
    private final EmergencyContactService emergencyContactService;

    public EmergencyContactController(EmergencyContactService emergencyContactService) {
        this.emergencyContactService = emergencyContactService;
    }

    @PostMapping("/add")
    public ResponseEntity<EmergencyContact> addContact(@RequestBody EmergencyContact contact) {
        return ResponseEntity.status(201).body(emergencyContactService.addContact(contact));
    }

    @GetMapping
    public ResponseEntity<List<EmergencyContact>> getAllContacts() {
        return ResponseEntity.ok(emergencyContactService.getAllContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmergencyContact> getContactById(@PathVariable Long id) {
        return ResponseEntity.ok(emergencyContactService.getContactById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        emergencyContactService.deleteContact(id);
        return ResponseEntity.ok().build();
    }
}
