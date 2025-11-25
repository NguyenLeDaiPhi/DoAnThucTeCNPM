// package com.e_health_care.web.patient.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.e_health_care.web.patient.model.Patient;
// import com.e_health_care.web.patient.repository.PatientRepository;

// @RestController
// @RequestMapping("/api")
// public class PatientFetchInformationAPI {
//     @Autowired
//     private PatientRepository patientRepository;

//     @GetMapping("/patient")
//     public List<Patient> getAllPatients() {
//         return patientRepository.findAll();
//     }
// }
