package com.crud.repository;

import com.crud.entity.Appointment;
import com.crud.entity.Doctor;
import com.crud.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Get all appointments by doctor
    List<Appointment> findByDoctor(Doctor doctor);

    // Get all appointments by user profile
    List<Appointment> findByUserProfile(UserProfile userProfile);

    // ✅ NEW: Check if a time slot is already booked for a doctor on a date+time
    boolean existsByDoctorAndAppointmentDateAndAppointmentTime(
            Doctor doctor,
            LocalDate appointmentDate,
            LocalTime appointmentTime
    );

    // Optional: still useful if you want to show user's appointments on a date
    List<Appointment> findByUserProfileAndAppointmentDate(UserProfile userProfile, LocalDate appointmentDate);
}
