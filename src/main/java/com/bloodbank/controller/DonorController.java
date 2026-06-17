package com.bloodbank.controller;

import com.bloodbank.model.Donor;
import com.bloodbank.service.DonorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/donors")
@CrossOrigin(origins = "*")
public class DonorController {
    private final DonorService donorService;

    public DonorController(DonorService donorService) {
        this.donorService = donorService;
    }

    @PostMapping("/register")
    public ResponseEntity<Donor> registerDonor(@RequestBody Donor donor) {
        return ResponseEntity.status(201).body(donorService.registerDonor(donor));
    }

    @GetMapping
    public ResponseEntity<List<Donor>> getAllDonors() {
        return ResponseEntity.ok(donorService.getAllDonors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Long id) {
        return ResponseEntity.ok(donorService.getDonorById(id));
    }

    @GetMapping("/{id}/donation-history")
    public ResponseEntity<List<?>> getDonationHistory(@PathVariable Long id) {
        return ResponseEntity.ok(donorService.getDonationHistory(id));
    }

    @PostMapping("/{id}/donate")
    public ResponseEntity<Donor> recordDonation(@PathVariable Long id,
                                                @RequestParam int units,
                                                @RequestParam String location) {
        return ResponseEntity.ok(donorService.recordDonation(id, units, location));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        donorService.deleteDonor(id);
        return ResponseEntity.ok().build();
    }
}
