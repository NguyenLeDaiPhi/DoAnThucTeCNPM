package com.e_health_care.web.doctor.service;

import com.e_health_care.web.patient.dto.PatientClinicalInforDTO;
import com.e_health_care.web.patient.model.PatientClinicalInfor;
import com.e_health_care.web.patient.repository.PatientClinicalInforRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e_health_care.web.patient.dto.PatientSummaryDTO;
import com.e_health_care.web.patient.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorViewPatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientClinicalInforRepository patientClinicalInforRepository;


    public List<PatientSummaryDTO> getAllPatients() {
        return patientRepository.findAll()
            .stream()
            .map(p -> {
                PatientSummaryDTO dto = new PatientSummaryDTO();
                dto.setId(p.getId());
                dto.setEmail(p.getEmail());
                dto.setFullName(p.getFirstName() + " " + p.getLastName());
                return dto;
            })
            .toList();
    }

    public PatientClinicalInforDTO getPatientClinicalInfo(Long patientId) {
        Optional<PatientClinicalInfor> clinicalInforOpt = patientClinicalInforRepository.findByPatientId(patientId);
        if (clinicalInforOpt.isPresent()) {
            PatientClinicalInfor clinicalInfor = clinicalInforOpt.get();
            PatientClinicalInforDTO dto = new PatientClinicalInforDTO();
            dto.setId(clinicalInfor.getId());
            dto.setPatientId(clinicalInfor.getPatient().getId());
            dto.setBloodType(clinicalInfor.getBloodType());
            dto.setAllergies(clinicalInfor.getAllergies());
            dto.setChronicDiseases(clinicalInfor.getChronicDiseases());
            dto.setFamilyMedicalHistory(clinicalInfor.getFamilyMedicalHistory());
            if (clinicalInfor.getLastUpDatedTime() != null) {
                // convert LocalDateTime to epoch millis to match Long parameter
                dto.setLastUpdatedById(clinicalInfor.getLastUpDatedTime()
                    .atZone(java.time.ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli());
            }
            return dto;
        }
        return null;
    }
}
