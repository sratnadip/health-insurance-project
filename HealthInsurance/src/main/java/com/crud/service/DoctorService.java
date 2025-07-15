package com.crud.service;

import com.crud.dto.DoctorRequestDTO;
import com.crud.entity.Doctor;
import java.util.List;

public interface DoctorService {

    // Save doctor using DTO
    Doctor saveDoctor(DoctorRequestDTO dto);

    // Save doctor directly via entity
    Doctor saveDoctor(Doctor doctor);

    // Get doctor by ID
    Doctor getDoctorById(Long id);

    // Get all doctors
    List<Doctor> getAllDoctors();

    // Update doctor
    Doctor updateDoctor(Long id, Doctor updatedDoctor);

    // Delete doctor by ID
    void deleteDoctor(Long id);
}
