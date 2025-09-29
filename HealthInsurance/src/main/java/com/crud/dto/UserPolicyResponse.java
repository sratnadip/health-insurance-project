package com.crud.dto;

import java.time.LocalDate;

public class UserPolicyResponse {



        private Long policyId;
        private Long userId;
        private String status;
        private LocalDate startDate;
        private LocalDate endDate;

        public UserPolicyResponse(Long policyId, Long userId,
                                  String status, LocalDate startDate, LocalDate endDate) {
            this.policyId = policyId;
            this.userId = userId;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public Long getPolicyId() { return policyId; }
        public Long getUserId() { return userId; }
        public String getStatus() { return status; }
        public LocalDate getStartDate() { return startDate; }
        public LocalDate getEndDate() { return endDate; }
    }


