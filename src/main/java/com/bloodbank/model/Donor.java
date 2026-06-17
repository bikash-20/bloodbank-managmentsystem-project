package com.bloodbank.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private Integer age;
    private Double weightKg;
    private String bloodGroup;
    private Boolean eligible;
    private LocalDate lastDonationDate;

    @OneToMany(mappedBy = "donor", cascade = CascadeType.ALL)
    private List<DonationRecord> donationHistory = new ArrayList<>();

    public Donor() {}

    public Donor(String name, String email, String phone, Integer age,
                 Double weightKg, String bloodGroup) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.weightKg = weightKg;
        this.bloodGroup = bloodGroup;
    }

    public Boolean checkEligibility() {
        if (age < 18 || age > 65) return false;
        if (weightKg < 50) return false;
        if (lastDonationDate != null) {
            LocalDate ninetyDaysAgo = LocalDate.now().minusDays(90);
            if (lastDonationDate.isAfter(ninetyDaysAgo)) return false;
        }
        return true;
    }

    public void recordDonation(DonationRecord record) {
        record.setDonor(this);
        donationHistory.add(record);
        lastDonationDate = record.getDonationDate();
        eligible = checkEligibility();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public Boolean getEligible() { return eligible; }
    public void setEligible(Boolean eligible) { this.eligible = eligible; }
    public LocalDate getLastDonationDate() { return lastDonationDate; }
    public void setLastDonationDate(LocalDate lastDonationDate) { this.lastDonationDate = lastDonationDate; }
    public List<DonationRecord> getDonationHistory() { return donationHistory; }
    public void setDonationHistory(List<DonationRecord> donationHistory) { this.donationHistory = donationHistory; }
}
