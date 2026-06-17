package com.bloodbank.interfaces;

import java.util.Map;

public interface BloodAvailable {
    Map<String, Integer> getAvailableStock();
    boolean isAvailable(String bloodGroup, int unitsNeeded);
    void addStock(String bloodGroup, int units);
    void reduceStock(String bloodGroup, int units);
}
