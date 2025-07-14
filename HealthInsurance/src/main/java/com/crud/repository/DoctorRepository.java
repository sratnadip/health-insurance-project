package com.crud.repository;

import com.crud.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Doctor findByDoctorName(String doctorName);

    List<Doctor> findByStatus(String status);

    List<Doctor> findByLocation(String location);
}
