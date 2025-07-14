package com.crud.service;

import com.crud.dto.DoctorRequestDTO;
import com.crud.entity.Doctor;
import java.util.List;

public interface DoctorService {
    Doctor saveDoctor(DoctorRequestDTO dto);


    Doctor saveDoctor(Doctor doctor);
    Doctor getDoctorById(Long id);
    List<Doctor> getAllDoctors();
    Doctor updateDoctor(Long id, Doctor updatedDoctor);
    void deleteDoctor(Long id);


}
