package com.e_health_care.web.patient.controller;

import com.e_health_care.web.patient.dto.UpdatePatientRequest;
import com.e_health_care.web.patient.dto.UpdatePatientResponse;
import com.e_health_care.web.patient.service.PatientService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private PatientService patientService;

    @PutMapping("/id")
    public ResponseEntity<?> updatePatient(@RequestBody UpdatePatientRequest request) {
        UpdatePatientResponse response = patientService.updatePatient(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProfilePatient(@PathVariable long id) {
        UpdatePatientResponse response = patientService.getProfilePatient(id);
        return ResponseEntity.ok(response);
    }
}
