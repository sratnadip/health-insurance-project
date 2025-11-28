package com.crud.entity;

import com.crud.enums.PolicyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_plan_id")
    private PolicyPlan policyPlan;

    private LocalDate startDate;
    private LocalDate endDate;
    private String nominee;
    private String nomineeRelation;

    private String policyStatus;


}
