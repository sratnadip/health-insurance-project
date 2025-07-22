package com.crud.serviceimpl;

import com.crud.entity.PolicyPlan;
import com.crud.repository.PolicyRepository;
import com.crud.service.PolicyPlanservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyPlanImpl implements PolicyPlanservice {

    @Autowired
    private PolicyRepository repository;


    @Override
    public PolicyPlan createPlan(PolicyPlan plan) {

        return repository.save(plan);
    }

    @Override
    public List<PolicyPlan> getAllPlans() {

        return repository.findAll();
    }
}
