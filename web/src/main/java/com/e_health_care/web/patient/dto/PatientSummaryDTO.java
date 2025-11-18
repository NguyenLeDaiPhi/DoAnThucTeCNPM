package com.e_health_care.web.patient.dto;

import lombok.Data;

@Data
public class PatientSummaryDTO {
    private long id;
    private String email;
    private String fullName;
}
