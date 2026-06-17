package com.bloodbank.service;

import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.model.EmergencyContact;
import com.bloodbank.repository.EmergencyContactRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmergencyContactService {
    private final EmergencyContactRepository emergencyContactRepository;

    public EmergencyContactService(EmergencyContactRepository emergencyContactRepository) {
        this.emergencyContactRepository = emergencyContactRepository;
    }

    public EmergencyContact addContact(EmergencyContact contact) {
        return emergencyContactRepository.save(contact);
    }

    public List<EmergencyContact> getAllContacts() {
        return emergencyContactRepository.findAll();
    }

    public EmergencyContact getContactById(Long id) {
        return emergencyContactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency contact not found with id: " + id));
    }

    public void deleteContact(Long id) {
        emergencyContactRepository.deleteById(id);
    }
}
