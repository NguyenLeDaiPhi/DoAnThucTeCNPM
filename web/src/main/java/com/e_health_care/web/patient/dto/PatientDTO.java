package com.e_health_care.web.patient.dto;

import lombok.Data;

@Data
public class PatientDTO {
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
}
