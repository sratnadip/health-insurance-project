package com.crud.repository;

import com.crud.entity.Admin;
import com.crud.enums.AdminStatus;
import com.crud.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByEmailAndStatus(String email, AdminStatus status);
    List<Admin> findByStatus(AdminStatus status);
    boolean existsByRole(Role role);
    Optional<Admin> findByGstNumber(String gstNumber);


}
