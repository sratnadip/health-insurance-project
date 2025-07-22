package com.crud.controller;

import com.crud.entity.PolicyPlan;
import com.crud.service.PolicyPlanservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminpolicyController {


    @Autowired
    private PolicyPlanservice service;

    @PostMapping("/policy-plans")
    public PolicyPlan addPlan(@RequestBody PolicyPlan plan) {
        return service.createPlan(plan);
    }

    @GetMapping("/policy-plans")
    public List<PolicyPlan> getPlans() {
        return service.getAllPlans();
    }
}