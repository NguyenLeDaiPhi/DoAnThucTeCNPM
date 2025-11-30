package com.e_health_care.web.patient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e_health_care.web.patient.dto.PatientClinicalInforDTO;
import com.e_health_care.web.patient.service.PatientRecordService;

@Controller
@RequestMapping("/patient")
public class PatientClinicalInforController {
    @Autowired
    private PatientRecordService patientRecordService;

    @GetMapping("/clinical-infor/{id}")
    public String showPatientClient(@PathVariable("id") Long id, Model model) {
        PatientClinicalInforDTO dto = patientRecordService.getClinicalForEdit(id);
        dto.setPatientId(id);
        model.addAttribute("clinicalInforDTO", dto);
        model.addAttribute("isDoctorView", false);
        return "patient-clinical-info";
    }
}
