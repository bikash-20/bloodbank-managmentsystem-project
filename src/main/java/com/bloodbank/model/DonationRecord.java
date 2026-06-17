package com.bloodbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class DonationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate donationDate;
    private Integer unitsDonated;
    private String location;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    @JsonIgnore
    private Donor donor;

    public DonationRecord() {}

    public DonationRecord(LocalDate donationDate, Integer unitsDonated, String location) {
        this.donationDate = donationDate;
        this.unitsDonated = unitsDonated;
        this.location = location;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDonationDate() { return donationDate; }
    public void setDonationDate(LocalDate donationDate) { this.donationDate = donationDate; }
    public Integer getUnitsDonated() { return unitsDonated; }
    public void setUnitsDonated(Integer unitsDonated) { this.unitsDonated = unitsDonated; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Donor getDonor() { return donor; }
    public void setDonor(Donor donor) { this.donor = donor; }
}
