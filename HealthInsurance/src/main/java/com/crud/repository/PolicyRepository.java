package com.crud.repository;

import com.crud.entity.PolicyPlan;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PolicyRepository extends JpaRepository<PolicyPlan, Long> {


}
