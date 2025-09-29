package com.crud.service;

import com.crud.dto.PolicyPlanRequest;
import com.crud.entity.PolicyPlan;

import java.util.List;

public interface PolicyPlanservice {

    PolicyPlan createPlan(PolicyPlanRequest request, Long adminId);
    PolicyPlan updatePlan(Long planId, PolicyPlanRequest request, Long adminId);
    void deletePlan(Long planId, Long adminId);
    List<PolicyPlan> getPlansByAdmin(Long adminId);
    List<PolicyPlan> getAllPlans();
    PolicyPlan getPlanById(Long planId);
}
