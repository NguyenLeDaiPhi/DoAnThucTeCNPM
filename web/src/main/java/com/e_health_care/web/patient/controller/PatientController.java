package com.e_health_care.web.patient.controller;

import com.e_health_care.web.patient.dto.UpdatePatientRequest;
import com.e_health_care.web.patient.dto.UpdatePatientResponse;
import com.e_health_care.web.patient.service.PatientService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private PatientService patientService;

    @PutMapping("/id")
    public ResponseEntity<?> updatePatient(@RequestBody UpdatePatientRequest request) {
        UpdatePatientResponse response = patientService.updatePatient(request);
        return ResponseEntity.ok(response);
    }
}
