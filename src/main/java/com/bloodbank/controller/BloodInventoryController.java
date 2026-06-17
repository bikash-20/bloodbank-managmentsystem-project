package com.bloodbank.controller;

import com.bloodbank.interfaces.BloodAvailable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/inventory")
@CrossOrigin(origins = "*")
public class BloodInventoryController {
    private final BloodAvailable bloodInventoryService;

    public BloodInventoryController(BloodAvailable bloodInventoryService) {
        this.bloodInventoryService = bloodInventoryService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Integer>> getInventory() {
        return ResponseEntity.ok(bloodInventoryService.getAvailableStock());
    }

    @PutMapping("/{bloodGroup}/add")
    public ResponseEntity<Void> addStock(@PathVariable String bloodGroup, @RequestParam int units) {
        bloodInventoryService.addStock(bloodGroup, units);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{bloodGroup}/reduce")
    public ResponseEntity<Void> reduceStock(@PathVariable String bloodGroup, @RequestParam int units) {
        bloodInventoryService.reduceStock(bloodGroup, units);
        return ResponseEntity.ok().build();
    }
}
