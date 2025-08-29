package com.crud.entity;

import com.crud.enums.AdminStatus;
import com.crud.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true, nullable = false)
    private String gstNumber;


    @Enumerated(EnumType.STRING)
    private AdminStatus status = AdminStatus.PENDING;

    private String otp; // temporary OTP for login

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getGstNumber() { return gstNumber; }
    public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }

    public AdminStatus getStatus() { return status; }
    public void setStatus(AdminStatus status) { this.status = status; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }
}
