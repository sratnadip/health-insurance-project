package com.crud.service;

import com.crud.entity.Admin;
import com.crud.enums.AdminStatus;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin registerAdmin(Admin admin);
    Optional<Admin> findByEmail(String email);
    List<Admin> getAllAdmins();
    List<Admin> getAdminsByStatus(AdminStatus status);
    Admin updateStatus(Long adminId, AdminStatus status); // for approval/rejection
    Admin save(Admin admin);
    Optional<Admin> findByGstNumber(String gstNumber);
}
