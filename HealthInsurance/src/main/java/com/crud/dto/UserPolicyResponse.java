package com.crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPolicyResponse {

        private Long policyId;
        private Long userId;
        private String status;
        private LocalDate startDate;
        private LocalDate endDate;
        private String nominee;
        private String nomineeRelation;


}


