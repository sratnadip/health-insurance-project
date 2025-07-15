package com.crud.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String doctorName;
    private String specialization;
    private String status;
    private String location;

    public Doctor() {}

    public Doctor(String specialization) {
        this.specialization = specialization;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getDoctorName() { return doctorName; }

    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getSpecialization() { return specialization; }

    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }



}
