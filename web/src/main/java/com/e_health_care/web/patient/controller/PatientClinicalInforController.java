package com.e_health_care.web.patient.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e_health_care.web.doctor.model.Doctor;
import com.e_health_care.web.doctor.repository.DoctorRepository;
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

    @Autowired
    private DoctorRepository doctorRepository;

    @GetMapping("/clinical-infor/{id}")
    public String showPatientClient(@PathVariable("id") Long id, Model model) {
        PatientClinicalInforDTO dto = patientRecordService.getClinicalForEdit(id);
        dto.setPatientId(id);
        model.addAttribute("clinicalInforDTO", dto);
        // Fetch all doctor
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "patient-clinical-info";
    }

    @PostMapping("/clinical-infor/{id}")
    public String updatePatientClient(@PathVariable("id") Long patientId, PatientClinicalInforDTO clinicalInforDTO, Model model, Principal principal) {
        // Assuming the updater is a logged-in Doctor.
        // The principal should be the doctor's email/username.
        String patientEmail = principal.getName();
        Optional<Patient> patient = patientRepository.findByEmail(patientEmail);
        if (!patient.isPresent()) {
            throw new UsernameNotFoundException("Patient not found with email: " + patientEmail);
        }
        
        // Use the doctor ID selected in the form (dto.getLastUpdatedById()) as the updater.
        // The last argument is the ID of the logged-in patient who initiated the update.
        patientRecordService.updateClinicalRecord(clinicalInforDTO.getLastUpdatedById(), patientId, clinicalInforDTO, patient.get().getId());
        model.addAttribute("success", "Đã cập nhật hồ sơ bệnh án thành công.");

        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        model.addAttribute("clincicalInforDTO", clinicalInforDTO);
        return "patient-clinical-info";
    }
}
