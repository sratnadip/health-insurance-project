package com.crud.repository;

import com.crud.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Get doctor by exact name (case-sensitive)
    Doctor findByDoctorName(String doctorName);

    // Optional: Case-insensitive search
    List<Doctor> findByDoctorNameIgnoreCaseContaining(String doctorName);

    // Get doctors by status (e.g., "Available", "On Leave")
    List<Doctor> findByStatus(String status);

    // Get doctors by location
    List<Doctor> findByLocation(String location);

    // Optional: Combine filters
    List<Doctor> findByLocationAndStatus(String location, String status);
}
