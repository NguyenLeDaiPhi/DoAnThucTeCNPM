package com.e_health_care.web.patient.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdatePatientResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private int phone;
}
