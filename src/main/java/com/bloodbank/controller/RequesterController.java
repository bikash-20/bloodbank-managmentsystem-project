package com.bloodbank.controller;

import com.bloodbank.model.Requester;
import com.bloodbank.service.RequesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requesters")
@CrossOrigin(origins = "*")
public class RequesterController {
    private final RequesterService requesterService;

    public RequesterController(RequesterService requesterService) {
        this.requesterService = requesterService;
    }

    @PostMapping("/register")
    public ResponseEntity<Requester> registerRequester(@RequestBody Requester requester) {
        return ResponseEntity.status(201).body(requesterService.registerRequester(requester));
    }
}
