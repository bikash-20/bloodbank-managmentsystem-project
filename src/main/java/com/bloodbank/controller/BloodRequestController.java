package com.bloodbank.controller;

import com.bloodbank.model.BloodRequest;
import com.bloodbank.service.BloodRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/blood-requests")
@CrossOrigin(origins = "*")
public class BloodRequestController {
    private final BloodRequestService bloodRequestService;

    public BloodRequestController(BloodRequestService bloodRequestService) {
        this.bloodRequestService = bloodRequestService;
    }

    @PostMapping("/submit")
    public ResponseEntity<BloodRequest> submitRequest(@RequestBody BloodRequest request) {
        return ResponseEntity.status(201).body(bloodRequestService.submitRequest(request));
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<BloodRequest> approveRequest(@PathVariable Long id) {
        return ResponseEntity.ok(bloodRequestService.approveRequest(id));
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<BloodRequest> rejectRequest(@PathVariable Long id) {
        return ResponseEntity.ok(bloodRequestService.rejectRequest(id));
    }

    @GetMapping
    public ResponseEntity<List<BloodRequest>> getAllRequests() {
        return ResponseEntity.ok(bloodRequestService.getAllRequests());
    }

    @GetMapping("/pending")
    public ResponseEntity<List<BloodRequest>> getPendingRequests() {
        return ResponseEntity.ok(bloodRequestService.getPendingRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodRequest> getRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(bloodRequestService.getRequestById(id));
    }
}
