package com.bloodbank.service;

import com.bloodbank.exception.DonorNotEligibleException;
import com.bloodbank.exception.InvalidDonorDataException;
import com.bloodbank.exception.ResourceNotFoundException;
import com.bloodbank.model.DonationRecord;
import com.bloodbank.model.Donor;
import com.bloodbank.repository.DonationRecordRepository;
import com.bloodbank.repository.DonorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class DonorService {

    private static final Pattern BLOOD_GROUP_PATTERN = Pattern.compile("^(A|B|AB|O)[+-]$");

    private final DonorRepository donorRepository;
    private final DonationRecordRepository donationRecordRepository;
    private final BloodInventoryService bloodInventoryService;

    public DonorService(DonorRepository donorRepository,
                        DonationRecordRepository donationRecordRepository,
                        BloodInventoryService bloodInventoryService) {
        this.donorRepository = donorRepository;
        this.donationRecordRepository = donationRecordRepository;
        this.bloodInventoryService = bloodInventoryService;
    }

    public Donor registerDonor(Donor donor) {
        validateDonor(donor);
        donor.setEligible(donor.checkEligibility());
        return donorRepository.save(donor);
    }

    private void validateDonor(Donor donor) {
        if (donor.getName() == null || donor.getName().isBlank())
            throw new InvalidDonorDataException("Donor name cannot be empty");
        if (donor.getBloodGroup() == null || !BLOOD_GROUP_PATTERN.matcher(donor.getBloodGroup()).matches())
            throw new InvalidDonorDataException("Invalid blood group '" + donor.getBloodGroup() + "'. Use formats like A+, O-, AB+");
        if (donor.getAge() == null || donor.getAge() < 1 || donor.getAge() > 120)
            throw new InvalidDonorDataException("Donor age must be realistic (1-120)");
        if (donor.getWeightKg() == null || donor.getWeightKg() <= 0)
            throw new InvalidDonorDataException("Donor weight must be a positive number");
    }

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + id));
    }

    public List<DonationRecord> getDonationHistory(Long donorId) {
        return donationRecordRepository.findByDonorIdOrderByDonationDateDesc(donorId);
    }

    @Transactional
    public Donor recordDonation(Long donorId, int units, String location) {
        Donor donor = getDonorById(donorId);
        if (!donor.checkEligibility()) {
            throw new DonorNotEligibleException(
                    donor.getName() + " is not currently eligible to donate " +
                    "(check age 18-65, weight >= 50kg, and 90-day gap since last donation)");
        }
        DonationRecord record = new DonationRecord(LocalDate.now(), units, location);
        donor.recordDonation(record);
        donationRecordRepository.save(record);
        donorRepository.save(donor);
        bloodInventoryService.addStock(donor.getBloodGroup(), units);
        return donor;
    }

    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }
}
