package com.e_health_care.web.patient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdatePatientRequest {
    private long id;
    private String email;
    private String fullName;
    private String address;
    private int phone;
}
