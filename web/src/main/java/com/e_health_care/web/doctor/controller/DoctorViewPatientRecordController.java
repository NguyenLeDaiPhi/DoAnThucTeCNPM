package com.e_health_care.web.doctor.controller;

import com.e_health_care.web.doctor.model.Doctor;
import com.e_health_care.web.doctor.repository.DoctorRepository;
import com.e_health_care.web.patient.dto.PatientClinicalInforDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e_health_care.web.doctor.service.DoctorViewPatientService;
import com.e_health_care.web.patient.dto.PatientSummaryDTO;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorViewPatientRecordController {

    @Autowired
    private DoctorViewPatientService doctorViewPatientService;

    @Autowired
    private DoctorRepository doctorRepository;
    
    @GetMapping("/dashboard")
    public String viewPatientClinical(Model model, HttpServletRequest request) {
        List<PatientSummaryDTO> patients = doctorViewPatientService.getAllPatients();
        model.addAttribute("patients", patients);
        
        // Get token from request attribute (set in JwtFilter) and add to model
        model.addAttribute("doctorToken", request.getAttribute("doctorToken"));

        return "doctor-dashboard";
    }

    @GetMapping("/patient/{id}")
    public String viewPatientClinicalRecord(@PathVariable("id") Long patientId, Model model) {
        PatientClinicalInforDTO clinicalInforDTO = doctorViewPatientService.getPatientClinicalInfo(patientId);
        if (clinicalInforDTO == null) {
            clinicalInforDTO = new PatientClinicalInforDTO();
            clinicalInforDTO.setPatientId(patientId);
        }
        model.addAttribute("clinicalInforDTO", clinicalInforDTO);
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        model.addAttribute("isDoctorView", true);
        return "patient-clinical-info";
    }
}
