package com.crud.serviceimpl;

import com.crud.entity.AdminProfile;
import com.crud.repository.AdminProfileRepository;
import com.crud.service.AdminProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminProfileServiceImpl implements AdminProfileService {

    @Autowired
    private AdminProfileRepository adminRepository;

    @Override
    public AdminProfile createAdmin(AdminProfile admin) {
        return adminRepository.save(admin);
    }

    @Override
    public List<AdminProfile> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public AdminProfile getAdminById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id " + id));
    }

    @Override
    public AdminProfile updateAdmin(Long id, AdminProfile adminDetails) {
        AdminProfile admin = getAdminById(id);
        admin.setName(adminDetails.getName());
        admin.setEmail(adminDetails.getEmail());
        admin.setPassword(adminDetails.getPassword());
        admin.setPhoneNumber(adminDetails.getPhoneNumber());
        admin.setDateOfBirth(adminDetails.getDateOfBirth());
        admin.setCompanyName(adminDetails.getCompanyName());
        admin.setCompanyType(adminDetails.getCompanyType());

        // ✅ Update GST and PAN separately
        admin.setGstNumber(adminDetails.getGstNumber());
        admin.setPanNumber(adminDetails.getPanNumber());

        admin.setHeadOfficeAddress(adminDetails.getHeadOfficeAddress());
        admin.setCity(adminDetails.getCity());
        admin.setState(adminDetails.getState());
        admin.setCountry(adminDetails.getCountry());
        admin.setPinCode(adminDetails.getPinCode());
        return adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
}
