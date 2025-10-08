package com.crud.controller;

import com.crud.dto.DoctorRequestDTO;
import com.crud.entity.Doctor;
import com.crud.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/save")
    public ResponseEntity<?> createDoctor(@RequestBody DoctorRequestDTO dto) {
        try {
            Doctor savedDoctor = doctorService.saveDoctor(dto);
            return ResponseEntity.status(201).body(savedDoctor);
        } catch (Exception e) {
            e.printStackTrace(); // See actual error in console
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // DoctorController.java
    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }


    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorService.updateDoctor(id, doctor);
    }

    @DeleteMapping("/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "Doctor with ID " + id + " deleted successfully.";
    }
}
