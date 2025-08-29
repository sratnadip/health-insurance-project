package com.crud.controller;

import com.crud.confg.JwtUtil;
import com.crud.entity.Admin;
import com.crud.enums.AdminStatus;
import com.crud.enums.Role;
import com.crud.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // DTO for login
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // DTO for OTP verification
    public static class VerifyOtpRequest {
        private String email;
        private String password;
        private String otp;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getOtp() { return otp; }
        public void setOtp(String otp) { this.otp = otp; }
    }

    // Register
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Admin admin) {
        try {
            Admin saved = adminService.registerAdmin(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    // Get all admins
    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    // Get pending admins
    @GetMapping("/pending")
    public ResponseEntity<List<Admin>> getPendingAdmins() {
        return ResponseEntity.ok(adminService.getAdminsByStatus(AdminStatus.PENDING));
    }

    // Approve Admin
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.updateStatus(id, AdminStatus.APPROVED));
    }

    // Reject Admin
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> rejectAdmin(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.updateStatus(id, AdminStatus.REJECTED));
    }

    // Login (send OTP) -> requires email + password
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Admin> optionalAdmin = adminService.findByEmail(request.getEmail());

        if (optionalAdmin.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin not found");

        Admin admin = optionalAdmin.get();

        if (admin.getStatus() != AdminStatus.APPROVED)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Admin not approved by SuperAdmin");

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        // If SUPER_ADMIN, skip OTP and return JWT directly
        if (admin.getRole() == Role.SUPER_ADMIN) {
            String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole().name());
            return ResponseEntity.ok(Map.of(
                    "message", "SUPER_ADMIN login successful",
                    "token", token
            ));
        }

        // Generate OTP (6 digits)
        String otp = String.format("%06d", new Random().nextInt(1_000_000));
        admin.setOtp(otp);
        adminService.save(admin);

        // Send OTP email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(admin.getEmail());
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);

        return ResponseEntity.ok("OTP sent to email");
    }

    // Verify OTP + JWT Token -> requires email + password + otp
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        Optional<Admin> optionalAdmin = adminService.findByEmail(request.getEmail());

        if (optionalAdmin.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin not found");

        Admin admin = optionalAdmin.get();

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
        }

        // Null-safe OTP check
        if (admin.getOtp() == null || request.getOtp() == null || !request.getOtp().equals(admin.getOtp())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }

        // Generate JWT
        String token = jwtUtil.generateToken(admin.getEmail(), admin.getRole().name());



        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", admin.getEmail());
        response.put("role", admin.getRole());

        return ResponseEntity.ok(response);
    }
}
