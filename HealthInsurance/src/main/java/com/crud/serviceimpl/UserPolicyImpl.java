package com.crud.serviceimpl;

import com.crud.dto.PurchaseRequest;
import com.crud.entity.PolicyPlan;
import com.crud.entity.UserPolicy;
import com.crud.repository.PolicyRepository;
import com.crud.repository.UserPolicyRepository;
import com.crud.service.UserPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserPolicyImpl implements UserPolicyService {

    @Autowired
    private PolicyRepository planRepository;

    @Autowired
    private UserPolicyRepository userPolicyRepository;

    @Override
    public UserPolicy purchasePolicy(PurchaseRequest request) {
        PolicyPlan plan = planRepository.findById(request.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy Plan not found"));

        UserPolicy userPolicy = UserPolicy.builder()
                .userId(request.getUserId())
                .policyPlan(plan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusYears(plan.getDurationInYears()))
                .policyStatus("ACTIVE")
                .nominee(request.getNominee())
                .nomineeRelation(request.getNomineeRelation())
                .build();

        return userPolicyRepository.save(userPolicy);
    }

    @Override
    public UserPolicy getPolicyByUserId(Long userId) {
        return userPolicyRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Policy not found for user ID: " + userId));
    }

    @Override
    public List<UserPolicy> getAllPoliciesByUserId(Long userId) {
        return userPolicyRepository.findAllByUserId(userId);
    }


}
