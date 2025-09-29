package com.crud.controller;

import com.crud.dto.PurchaseRequest;
import com.crud.dto.UserPolicyResponse;
import com.crud.entity.PolicyPlan;
import com.crud.entity.UserPolicy;
import com.crud.service.PolicyPlanservice;
import com.crud.service.UserPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserPolicyController {

    @Autowired
    private PolicyPlanservice policyPlanService;

    @Autowired
    private UserPolicyService userPolicyService;

    @GetMapping("/fetch-plan")
    public List<PolicyPlan> getAvailablePlans() {

        return policyPlanService.getAllPlans();
    }

    @PostMapping("/add-policy")
    public UserPolicyResponse purchase(@RequestBody PurchaseRequest request) {
        UserPolicy userPolicy = userPolicyService.purchasePolicy(request);

        return new UserPolicyResponse(
                userPolicy.getId(),          // policyId
                userPolicy.getUserId(),
                userPolicy.getPolicyStatus(),
                userPolicy.getStartDate(),
                userPolicy.getEndDate()
        );
    }


    //    @GetMapping("/get-policy/{userId}")
//    public ResponseEntity<UserPolicy> getPolicyByUserId(@PathVariable Long userId) {
//        UserPolicy userPolicy = userPolicyService.getPolicyByUserId(userId);
//        return ResponseEntity.ok(userPolicy);
//
//    }
    @GetMapping("/get-policies/{userId}")
    public ResponseEntity<List<UserPolicy>> getPoliciesByUserId(@PathVariable Long userId) {
        List<UserPolicy> policies = userPolicyService.getAllPoliciesByUserId(userId);
        return ResponseEntity.ok(policies);
    }


}
