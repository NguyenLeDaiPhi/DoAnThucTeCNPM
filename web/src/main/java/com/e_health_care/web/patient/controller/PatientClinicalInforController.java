package com.e_health_care.web.patient.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e_health_care.web.patient.dto.PatientClinicalInforDTO;
import com.e_health_care.web.patient.model.Patient;
import com.e_health_care.web.patient.repository.PatientRepository;
import com.e_health_care.web.patient.service.PatientRecordService;

@Controller
@RequestMapping("/patient")
public class PatientClinicalInforController {
    @Autowired
    private PatientRecordService patientRecordService;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/clinical-infor/{id}")
    public String showPatientClient(@PathVariable("id") Long id, Model model) {
        PatientClinicalInforDTO dto = patientRecordService.getClinicalForEdit(id);
        dto.setPatientId(id);
        model.addAttribute("clinicalInforDTO", dto);
        return "patient-clinical-info";
    }

    @PostMapping("/clinical-infor/{id}")
    public String updatePatientClient(@PathVariable("id") Long patientId, PatientClinicalInforDTO clinicalInforDTO, Model model, Principal principal, org.springframework.security.core.Authentication authentication) {
        // Check if the current user has ROLE_DOCTOR - if so, deny the update
        boolean isDoctor = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> "ROLE_DOCTOR".equals(a.getAuthority()));
        if (isDoctor) {
            model.addAttribute("error", "Bác sĩ không có quyền cập nhật thông tin bệnh án của bệnh nhân.");
            model.addAttribute("clinicalInforDTO", clinicalInforDTO);
            model.addAttribute("isDoctorView", true);
            return "patient-clinical-info";
        }

        // The principal is the logged-in patient.
        String patientEmail = principal.getName();
        Optional<Patient> patient = patientRepository.findByEmail(patientEmail);
        if (!patient.isPresent()) {
            throw new UsernameNotFoundException("Patient not found with email: " + patientEmail);
        }
        
        // The last argument is the ID of the logged-in patient who initiated the update.
        patientRecordService.updateClinicalRecord(patientId, clinicalInforDTO, patient.get().getId());
        model.addAttribute("success", "Đã cập nhật hồ sơ bệnh án thành công.");

        model.addAttribute("clinicalInforDTO", clinicalInforDTO);
        return "patient-clinical-info";
    }
}
