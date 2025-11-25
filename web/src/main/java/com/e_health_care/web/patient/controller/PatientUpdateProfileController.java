package com.e_health_care.web.patient.controller;

import com.e_health_care.web.patient.dto.PatientDTO;
import com.e_health_care.web.patient.model.Patient;
import com.e_health_care.web.patient.repository.PatientRepository;
import com.e_health_care.web.patient.service.PatientUpdateProfileService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/patient")
public class PatientUpdateProfileController {
    @Autowired private PatientUpdateProfileService patientService;
    @Autowired private PatientRepository patientRepository;

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        PatientDTO patientDTO = patientService.getPatientById(id);
        model.addAttribute("patient", patientDTO);
        return "patient-update";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") Long id,
                                @ModelAttribute("patient") PatientDTO patientDTO,
                                Model model) {
        patientService.updatePatient(patientDTO, id);
        model.addAttribute("message", "Cập nhật thành công!");
        return "patient-update";
    }
    
    @GetMapping("/index")
    public String patientIndex(Model model, Principal principal, HttpServletRequest request) {
        String email = principal.getName();
        Patient patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        model.addAttribute("patient", patient);

        // Get token from request attribute (set in JwtFilter) and add to model
        model.addAttribute("patientToken", request.getAttribute("patientToken"));

        return "patient-success";   
    }
}
