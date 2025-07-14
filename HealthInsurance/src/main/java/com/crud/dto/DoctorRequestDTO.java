package com.crud.dto;

public class DoctorRequestDTO {
    private String doctorName;
    private String specialization;
    private String status;
    private String location;
    private Long userProfileId;

    public String getDoctorName() {
        return doctorName;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
