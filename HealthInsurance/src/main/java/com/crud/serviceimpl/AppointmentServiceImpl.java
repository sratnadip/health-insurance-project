package com.crud.service.impl;

import com.crud.dto.AppointmentRequest;
import com.crud.entity.Appointment;
import com.crud.entity.Doctor;
import com.crud.entity.UserProfile;
import com.crud.repository.AppointmentRepository;
import com.crud.repository.DoctorRepository;
import com.crud.repository.UserProfileRepository;
import com.crud.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public Appointment bookAppointment(AppointmentRequest request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        UserProfile userProfile = userProfileRepository.findById(request.getUserProfileId())
                .orElseThrow(() -> new RuntimeException("User Profile not found"));

        // ✅ Check if doctor is already booked at this date + time
        boolean exists = appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(
                doctor,
                request.getAppointmentDate(),
                request.getAppointmentTime()
        );

        if (exists) {
            throw new RuntimeException("This time slot is already booked for the doctor.");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setUserProfile(userProfile);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentTime(request.getAppointmentTime()); // 🕒 Set time
        appointment.setStatus("Scheduled");

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByDoctorId(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentRepository.findByDoctor(doctor);
    }

    @Override
    public List<Appointment> getAppointmentsByUserProfileId(Long userProfileId) {
        UserProfile userProfile = userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new RuntimeException("User Profile not found"));

        return appointmentRepository.findByUserProfile(userProfile);
    }
}
