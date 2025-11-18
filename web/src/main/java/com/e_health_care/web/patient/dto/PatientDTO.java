package com.e_health_care.web.patient.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientDTO {
    private long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private int phone;

    private String role = "patient";

    public String getUppercase_role() {
        return this.role.toUpperCase();
    }

    private LocalDate dateOfBirth; // Example field
    private String medicalHistory; // Example field for records
}
