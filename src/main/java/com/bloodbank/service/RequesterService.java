package com.bloodbank.service;

import com.bloodbank.exception.InvalidDonorDataException;
import com.bloodbank.model.Requester;
import com.bloodbank.repository.RequesterRepository;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

@Service
public class RequesterService {
    private static final Pattern BLOOD_GROUP_PATTERN = Pattern.compile("^(A|B|AB|O)[+-]$");
    private final RequesterRepository requesterRepository;

    public RequesterService(RequesterRepository requesterRepository) {
        this.requesterRepository = requesterRepository;
    }

    public Requester registerRequester(Requester requester) {
        if (requester.getName() == null || requester.getName().isBlank())
            throw new InvalidDonorDataException("Requester name cannot be empty");
        if (requester.getBloodGroup() == null || !BLOOD_GROUP_PATTERN.matcher(requester.getBloodGroup()).matches())
            throw new InvalidDonorDataException("Invalid blood group '" + requester.getBloodGroup() + "'");
        return requesterRepository.save(requester);
    }
}
