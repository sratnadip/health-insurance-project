package com.crud.controller;

import com.crud.dto.AppointmentRequest;
import com.crud.entity.Appointment;
import com.crud.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // ✅ Book a new appointment (date + time)
    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody AppointmentRequest request) {
        return appointmentService.bookAppointment(request);
    }

    // ✅ Get all appointments for a doctor
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long doctorId) {
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }

    // ✅ Get all appointments for a user
    @GetMapping("/user/{userProfileId}")
    public List<Appointment> getAppointmentsByUser(@PathVariable Long userProfileId) {
        return appointmentService.getAppointmentsByUserProfileId(userProfileId);
    }
}
