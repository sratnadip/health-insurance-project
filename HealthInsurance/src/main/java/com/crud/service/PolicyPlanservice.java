package com.crud.service;

import com.crud.entity.PolicyPlan;

import java.util.List;

public interface PolicyPlanservice {

    PolicyPlan createPlan(PolicyPlan plan);
    List<PolicyPlan> getAllPlans();

}

