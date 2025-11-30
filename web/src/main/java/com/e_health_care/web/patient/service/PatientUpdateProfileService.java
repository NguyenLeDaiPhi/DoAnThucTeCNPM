package com.e_health_care.web.patient.service;

import com.e_health_care.web.patient.dto.PatientDTO;
import com.e_health_care.web.patient.model.Patient;
import com.e_health_care.web.patient.repository.PatientRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientUpdateProfileService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public PatientDTO getPatientById(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found patient"));

        PatientDTO patientDto = new PatientDTO();
        BeanUtils.copyProperties(patient, patientDto);
        return patientDto;
    }

    public void updatePatient(PatientDTO dto, long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found patient"));

        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName()); 
        patient.setAddress(dto.getAddress());
        patient.setPhone(dto.getPhone());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setMedicalHistory(dto.getMedicalHistory());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            patient.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        patientRepository.save(patient);
    }
}
