package com.crud.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    private String claimType;

    private LocalDate incidentDate;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Claim(Long claimId, String claimType, LocalDate incidentDate, Double amount, Long userId) {
        this.claimId = claimId;
        this.claimType = claimType;
        this.incidentDate = incidentDate;
        this.amount = amount;

    }

    public Claim() {

    }

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public String getClaimType() {
        return claimType;
    }

    public void setClaimType(String claimType) {
        this.claimType = claimType;
    }

    public LocalDate getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(LocalDate incidentDate) {
        this.incidentDate = incidentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}