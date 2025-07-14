package com.crud.serviceimpl;

import com.crud.dto.DoctorRequestDTO;
import com.crud.entity.Doctor;
import com.crud.entity.UserProfile;
import com.crud.repository.DoctorRepository;
import com.crud.repository.UserProfileRepository;
import com.crud.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor saveDoctor(DoctorRequestDTO dto) {
        Optional<UserProfile> userProfileOpt = userProfileRepository.findById(dto.getUserProfileId());
        if (userProfileOpt.isEmpty()) {
            throw new RuntimeException("UserProfile not found with id: " + dto.getUserProfileId());
        }

        Doctor doctor = new Doctor();
        doctor.setDoctorName(dto.getDoctorName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setStatus(dto.getStatus());
        doctor.setLocation(dto.getLocation());
        doctor.setUserProfile(userProfileOpt.get());

        return doctorRepository.save(doctor);
    }


    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor updatedDoctor) {
        Optional<Doctor> existingDoctorOpt = doctorRepository.findById(id);
        if (existingDoctorOpt.isPresent()) {
            Doctor existingDoctor = existingDoctorOpt.get();
            existingDoctor.setDoctorName(updatedDoctor.getDoctorName());
            existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
            existingDoctor.setStatus(updatedDoctor.getStatus());
            existingDoctor.setLocation(updatedDoctor.getLocation());
            return doctorRepository.save(existingDoctor);
        }
        return null;
    }

    @Override
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }

}
