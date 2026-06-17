package com.bloodbank.repository;
import com.bloodbank.model.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {}
