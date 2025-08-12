package com.crud.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;

@Entity
@Builder
public class UserPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "policy_plan_id")
    private PolicyPlan policyPlan;

    private LocalDate startDate;
    private LocalDate endDate;
    private String policyStatus;
    private String nominee;
    private String nomineeRelation;

    public UserPolicy(Long id, Long userId, PolicyPlan policyPlan, LocalDate startDate, LocalDate endDate, String policyStatus, String nominee, String nomineeRelation) {
        this.id = id;
        this.userId = userId;
        this.policyPlan = policyPlan;
        this.startDate = startDate;
        this.endDate = endDate;
        this.policyStatus = policyStatus;
        this.nominee = nominee;
        this.nomineeRelation = nomineeRelation;
    }

    public UserPolicy(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PolicyPlan getPolicyPlan() {
        return policyPlan;
    }

    public void setPolicyPlan(PolicyPlan policyPlan) {
        this.policyPlan = policyPlan;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }


}
