package com.crud.dto;

public class PurchaseRequest {

    private Long userId;
    private Long policyId;
    private String nominee;
    private String nomineeRelation;

    public PurchaseRequest(Long userId, Long policyId, String nominee, String nomineeRelation) {
        this.userId = userId;
        this.policyId = policyId;
        this.nominee = nominee;
        this.nomineeRelation = nomineeRelation;
    }

    public PurchaseRequest(){

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
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