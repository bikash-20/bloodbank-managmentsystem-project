package com.bloodbank.service;

import com.bloodbank.exception.InsufficientBloodStockException;
import com.bloodbank.interfaces.BloodAvailable;
import com.bloodbank.model.BloodStock;
import com.bloodbank.repository.BloodStockRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class BloodInventoryService implements BloodAvailable {

    private static final List<String> BLOOD_GROUPS = List.of(
            "A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"
    );

    private final BloodStockRepository bloodStockRepository;

    public BloodInventoryService(BloodStockRepository bloodStockRepository) {
        this.bloodStockRepository = bloodStockRepository;
    }

    @PostConstruct
    public void seedStockIfEmpty() {
        for (String group : BLOOD_GROUPS) {
            bloodStockRepository.findById(group)
                    .orElseGet(() -> bloodStockRepository.save(new BloodStock(group, 0)));
        }
    }

    @Override
    public Map<String, Integer> getAvailableStock() {
        Map<String, Integer> stockMap = new LinkedHashMap<>();
        for (String group : BLOOD_GROUPS) {
            int units = bloodStockRepository.findById(group)
                    .map(BloodStock::getUnitsAvailable).orElse(0);
            stockMap.put(group, units);
        }
        return stockMap;
    }

    @Override
    public boolean isAvailable(String bloodGroup, int unitsNeeded) {
        return bloodStockRepository.findById(bloodGroup)
                .map(stock -> stock.getUnitsAvailable() >= unitsNeeded)
                .orElse(false);
    }

    @Override
    public void addStock(String bloodGroup, int units) {
        BloodStock stock = bloodStockRepository.findById(bloodGroup)
                .orElseGet(() -> new BloodStock(bloodGroup, 0));
        stock.setUnitsAvailable(stock.getUnitsAvailable() + units);
        bloodStockRepository.save(stock);
    }

    @Override
    public void reduceStock(String bloodGroup, int units) {
        BloodStock stock = bloodStockRepository.findById(bloodGroup)
                .orElseThrow(() -> new InsufficientBloodStockException(
                        "No stock record found for blood group " + bloodGroup));
        if (stock.getUnitsAvailable() < units) {
            throw new InsufficientBloodStockException(
                    "Insufficient stock for " + bloodGroup + ". Available: "
                            + stock.getUnitsAvailable() + ", Requested: " + units);
        }
        stock.setUnitsAvailable(stock.getUnitsAvailable() - units);
        bloodStockRepository.save(stock);
    }

    public List<String> getSupportedBloodGroups() {
        return BLOOD_GROUPS;
    }
}
