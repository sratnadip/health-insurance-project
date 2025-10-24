package com.crud.controller;

import com.crud.entity.AdminProfile;
import com.crud.service.AdminProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminProfileController {

    @Autowired
    private AdminProfileService adminService;

    @PostMapping("/save")
    public AdminProfile createAdmin(@RequestBody AdminProfile admin) {
        return adminService.createAdmin(admin);
    }

    @GetMapping("/all")
    public List<AdminProfile> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public AdminProfile getAdminById(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    @PutMapping("/{id}")
    public AdminProfile updateAdmin(@PathVariable Long id, @RequestBody AdminProfile adminDetails) {
        return adminService.updateAdmin(id, adminDetails);
    }

    @DeleteMapping("/{id}")
    public String deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return "Admin deleted with id " + id;
    }
}
