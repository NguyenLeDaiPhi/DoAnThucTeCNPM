// package com.e_health_care.web.doctor.controller;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.e_health_care.web.doctor.model.Doctor;
// import com.e_health_care.web.doctor.repository.DoctorRepository;

// @RestController
// @RequestMapping("/api")
// public class DoctorFetchInformationAPI {
//     @Autowired
//     private DoctorRepository doctorRepository;

//     @GetMapping("/doctor")
//     public List<Doctor> getAllDoctors() {
//         return doctorRepository.findAll();
//     }
// }
