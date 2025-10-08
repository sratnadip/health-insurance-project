package com.crud.entity;

import com.crud.enums.AdminStatus;
import com.crud.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admins")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true, nullable = true)
    private String gstNumber;

    @Enumerated(EnumType.STRING)
    private AdminStatus status = AdminStatus.PENDING;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String otp; // temporary OTP for login

    //  One-to-One Mapping with AdminProfile
    @OneToOne(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonManagedReference
    private AdminProfile profile;

    //  One-to-Many Mapping with PolicyPlan
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<PolicyPlan> policyPlans = new ArrayList<>();


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getGstNumber() { return gstNumber; }
    public void setGstNumber(String gstNumber) { this.gstNumber = gstNumber; }

    public AdminStatus getStatus() { return status; }
    public void setStatus(AdminStatus status) { this.status = status; }

    public String getOtp() { return otp; }
    public void setOtp(String otp) { this.otp = otp; }

    public AdminProfile getProfile() { return profile; }
    public void setProfile(AdminProfile profile) {
        this.profile = profile;
        if (profile != null) {
            profile.setAdmin(this);
        }
    }

    public List<PolicyPlan> getPolicyPlans() { return policyPlans; }
    public void setPolicyPlans(List<PolicyPlan> policyPlans) { this.policyPlans = policyPlans; }
}

