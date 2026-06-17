package com.bloodbank.model;

import jakarta.persistence.*;

@Entity
public class BloodStock {

    @Id
    private String bloodGroup;

    private Integer unitsAvailable;

    public BloodStock() {}

    public BloodStock(String bloodGroup, Integer unitsAvailable) {
        this.bloodGroup = bloodGroup;
        this.unitsAvailable = unitsAvailable;
    }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public Integer getUnitsAvailable() { return unitsAvailable; }
    public void setUnitsAvailable(Integer unitsAvailable) { this.unitsAvailable = unitsAvailable; }
}
