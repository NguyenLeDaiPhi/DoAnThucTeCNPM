package com.e_health_care.web.patient.service;

import com.e_health_care.web.config.ModelMapperConfig;
import com.e_health_care.web.patient.dto.PatientDTO;
import com.e_health_care.web.patient.dto.UpdatePatientRequest;
import com.e_health_care.web.patient.dto.UpdatePatientResponse;
import com.e_health_care.web.patient.model.Patient;
import com.e_health_care.web.patient.repository.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional
    public UpdatePatientResponse updatePatient(UpdatePatientRequest request) {
        Patient patient = patientRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Not found patient"));

        modelMapper.map(request, patient);

        patientRepository.save(patient);

        return modelMapper.map(patient, UpdatePatientResponse.class);
    }

    public UpdatePatientResponse getProfilePatient(long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found patient"));

        return modelMapper.map(patient, UpdatePatientResponse.class);
    }
}
