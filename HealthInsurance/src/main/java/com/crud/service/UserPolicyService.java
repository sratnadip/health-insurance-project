package com.crud.service;

import com.crud.dto.PurchaseRequest;
import com.crud.entity.UserPolicy;

import java.util.List;
import java.util.Optional;

public interface UserPolicyService {

    UserPolicy purchasePolicy(PurchaseRequest request);
  //  Optional<UserPolicy> getPolicyById(Long id);
    UserPolicy getPolicyByUserId(Long userId);
    List<UserPolicy> getAllPoliciesByUserId(Long userId);

}
