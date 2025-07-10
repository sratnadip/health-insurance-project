package com.crud.entity;

import jakarta.persistence.*;

@Entity
public class PolicyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyId;

    private String planName;
    private Double coverageAmount;
    private Double premium;
    private Integer durationInYears; // used to calculate endDate

    public PolicyPlan(Long policyId, String planName, Double coverageAmount, Double premium, Integer durationInYears) {
        this.policyId = policyId;
        this.planName = planName;
        this.coverageAmount = coverageAmount;
        this.premium = premium;
        this.durationInYears = durationInYears;
    }

    public PolicyPlan(){

    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(Double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public Integer getDurationInYears() {
        return durationInYears;
    }

    public void setDurationInYears(Integer durationInYears) {
        this.durationInYears = durationInYears;
    }
}