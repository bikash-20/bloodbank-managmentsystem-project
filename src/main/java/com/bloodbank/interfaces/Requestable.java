package com.bloodbank.interfaces;

import com.bloodbank.model.BloodRequest;

public interface Requestable {
    BloodRequest submitRequest(BloodRequest request);
    BloodRequest approveRequest(Long requestId);
    BloodRequest rejectRequest(Long requestId);
}
