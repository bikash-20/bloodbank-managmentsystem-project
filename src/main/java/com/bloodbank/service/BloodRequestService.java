package com.bloodbank.service;

import com.bloodbank.exception.InsufficientBloodStockException;
import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.interfaces.Requestable;
import com.bloodbank.model.BloodRequest;
import com.bloodbank.model.RequestStatus;
import com.bloodbank.repository.BloodRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BloodRequestService implements Requestable {

    private final BloodRequestRepository bloodRequestRepository;
    private final BloodInventoryService bloodInventoryService;

    public BloodRequestService(BloodRequestRepository bloodRequestRepository,
                               BloodInventoryService bloodInventoryService) {
        this.bloodRequestRepository = bloodRequestRepository;
        this.bloodInventoryService = bloodInventoryService;
    }

    @Override
    public BloodRequest submitRequest(BloodRequest request) {
        request.setStatus(RequestStatus.PENDING);
        return bloodRequestRepository.save(request);
    }

    @Override
    @Transactional
    public BloodRequest approveRequest(Long requestId) {
        BloodRequest request = getRequestById(requestId);
        if (!bloodInventoryService.isAvailable(request.getBloodGroup(), request.getUnitsRequested())) {
            throw new InsufficientBloodStockException(
                    "Cannot approve request #" + requestId + ": insufficient stock of " + request.getBloodGroup());
        }
        bloodInventoryService.reduceStock(request.getBloodGroup(), request.getUnitsRequested());
        request.setStatus(RequestStatus.FULFILLED);
        return bloodRequestRepository.save(request);
    }

    @Override
    public BloodRequest rejectRequest(Long requestId) {
        BloodRequest request = getRequestById(requestId);
        request.setStatus(RequestStatus.REJECTED);
        return bloodRequestRepository.save(request);
    }

    public BloodRequest getRequestById(Long id) {
        return bloodRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood request not found with id: " + id));
    }

    public List<BloodRequest> getAllRequests() {
        return bloodRequestRepository.findAllByOrderByRequestDateDesc();
    }

    public List<BloodRequest> getPendingRequests() {
        return bloodRequestRepository.findByStatus(RequestStatus.PENDING);
    }
}
