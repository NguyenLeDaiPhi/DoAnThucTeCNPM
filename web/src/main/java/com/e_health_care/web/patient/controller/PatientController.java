package com.e_health_care.web.patient.controller;

import com.e_health_care.web.patient.dto.PatientDTO;
import com.e_health_care.web.patient.model.Patient;
import com.e_health_care.web.patient.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired private PatientService patientService;

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        PatientDTO patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        return "patient-update"; // trỏ tới templates/patient-update.html
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") Long id,
                                @ModelAttribute("patient") PatientDTO patientDTO,
                                Model model) {
        patientService.updatePatient(patientDTO, id);
        model.addAttribute("message", "Cập nhật thành công!");
        return "patient-update";
    }

}
