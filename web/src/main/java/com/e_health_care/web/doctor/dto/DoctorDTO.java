package com.e_health_care.web.doctor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorDTO {
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String role = "doctor";

    public String getUppercase_role() {
        return this.role.toUpperCase();
    }
}
