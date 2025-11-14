package com.e_health_care.web.patient.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_health_care.web.doctor.model.Doctor;
import com.e_health_care.web.doctor.repository.DoctorRepository;
import com.e_health_care.web.patient.dto.PatientClinicalInforDTO;
import com.e_health_care.web.patient.model.Patient;
import com.e_health_care.web.patient.model.PatientClinicalInfor;
import com.e_health_care.web.patient.repository.PatientClinicalInforRepository;
import com.e_health_care.web.patient.repository.PatientRepository;

import jakarta.transaction.Transactional;

@Service
public class PatientRecordService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientClinicalInforRepository inforRepository;

    @Transactional
    public PatientClinicalInfor updateClinicalRecord(Long doctorId, Long patientId, PatientClinicalInforDTO inforDTO, Long updaterId) {
        PatientClinicalInfor patientClinicalInfor = inforRepository.findByPatientId(patientId)
            .orElseGet(() -> {
                Patient patient = patientRepository.findById(patientId)
                    .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + patientId));

                PatientClinicalInfor newInfor = new PatientClinicalInfor();
                newInfor.setPatient(patient);
                return newInfor;
        });
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        patientClinicalInfor.setDoctor(doctor.orElse(null));
        // Update Data Field
        patientClinicalInfor.setAllergies(inforDTO.getAllergies());
        patientClinicalInfor.setBloodType(inforDTO.getBloodType());
        patientClinicalInfor.setChronicDiseases(inforDTO.getChronicDiseases());
        patientClinicalInfor.setFamilyMedicalHistory(inforDTO.getFamilyMedicalHistory());

        // Record Audit Information
        patientClinicalInfor.setLastUpDatedTime(LocalDateTime.now());

        return inforRepository.save(patientClinicalInfor);
    }

    public PatientClinicalInforDTO getClinicalForEdit(Long patientId) {
        return inforRepository.findByPatientId(patientId)
            .map(info -> {
                PatientClinicalInforDTO dto = new PatientClinicalInforDTO();
                dto.setBloodType(info.getBloodType());
                dto.setAllergies(info.getAllergies());
                dto.setChronicDiseases(info.getChronicDiseases());
                dto.setFamilyMedicalHistory(info.getFamilyMedicalHistory());
                return dto;
            })
            .orElseGet(() -> {
                PatientClinicalInforDTO dto = new PatientClinicalInforDTO();
                dto.setId(patientId);
                return dto;
            }); 
    }
}
