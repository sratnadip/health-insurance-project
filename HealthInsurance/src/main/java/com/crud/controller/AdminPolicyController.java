package com.crud.controller;

import com.crud.entity.PolicyPlan;
import com.crud.service.PolicyPlanservice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminPolicyController {

    @Autowired
    private PolicyPlanservice service;

    private final ObjectMapper mapper = new ObjectMapper();

    // -------------------- CREATE --------------------
    @PostMapping("/{adminId}/policy-plans")
    public ResponseEntity<PolicyPlan> createPolicy(
            @PathVariable Long adminId,
            @RequestParam("policy") String policyJson,
            @RequestParam(value = "image", required = false) MultipartFile file
    ) throws Exception {
        PolicyPlan plan = service.storePolicyWithImage(file, adminId, policyJson);
        return ResponseEntity.ok(plan);
    }

    // -------------------- UPDATE --------------------
    @PutMapping("/{adminId}/policy-plans/{planId}")
    public ResponseEntity<PolicyPlan> updatePolicy(
            @PathVariable Long adminId,
            @PathVariable Long planId,
            @RequestParam("policy") String policyJson,
            @RequestParam(value = "image", required = false) MultipartFile file
    ) throws Exception {
        PolicyPlan plan = service.updatePolicyWithImage(planId, file, policyJson, adminId);
        return ResponseEntity.ok(plan);
    }

    // -------------------- DELETE --------------------
    @DeleteMapping("/{adminId}/policy-plans/{planId}")
    public ResponseEntity<String> deletePolicy(
            @PathVariable Long adminId,
            @PathVariable Long planId
    ) {
        service.deletePlan(planId, adminId);
        return ResponseEntity.ok("Policy deleted successfully");
    }

    // -------------------- GET ALL --------------------
    @GetMapping("/policy-plans/all")
    public ResponseEntity<List<PolicyPlan>> getAllPolicies() {
        List<PolicyPlan> plans = service.getAllPlans();
        return ResponseEntity.ok(plans);
    }

    // -------------------- GET BY ADMIN --------------------
    @GetMapping("/{adminId}/policy-plans")
    public ResponseEntity<List<PolicyPlan>> getPoliciesByAdmin(@PathVariable Long adminId) {
        List<PolicyPlan> plans = service.getPlansByAdmin(adminId);
        return ResponseEntity.ok(plans);
    }

    // -------------------- VIEW IMAGE --------------------
    @GetMapping("/policy-plans/view-image/{planId}")
    public ResponseEntity<Resource> viewPolicyImage(@PathVariable Long planId) throws IOException {
        PolicyPlan plan = service.getPlanById(planId);
        String imagePath = plan.getImageUrl();
        if (imagePath == null || imagePath.isEmpty()) return ResponseEntity.notFound().build();

        Path filePath = Paths.get(imagePath).normalize();
        Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filePath.getFileName() + "\"")
                .body(resource);
    }

    // -------------------- DOWNLOAD IMAGE --------------------
    @GetMapping("/policy-plans/download-image/{planId}")
    public ResponseEntity<Resource> downloadPolicyImage(@PathVariable Long planId) throws IOException {
        PolicyPlan plan = service.getPlanById(planId);
        String imagePath = plan.getImageUrl();
        if (imagePath == null || imagePath.isEmpty()) return ResponseEntity.notFound().build();

        Path filePath = Paths.get(imagePath).normalize();
        Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filePath.getFileName() + "\"")
                .body(resource);
    }
}
