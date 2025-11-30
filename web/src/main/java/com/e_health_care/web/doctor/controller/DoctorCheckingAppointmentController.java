package com.e_health_care.web.doctor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.e_health_care.web.doctor.model.DoctorPrinciple;
import com.e_health_care.web.patient.model.Appointment;
import com.e_health_care.web.patient.service.PatientAppointmentService;

@Controller
@RequestMapping("/doctor")
public class DoctorCheckingAppointmentController {
    @Autowired
    private PatientAppointmentService patientAppointmentService;

    private Long getDoctorId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof DoctorPrinciple principle) {
            return principle.getDoctor().getId();
        }
        return null;
    }

    @GetMapping("/appointment")
    public String getAppointmentFromPatient(Model model) {
        Long doctorId = getDoctorId();
        List<Appointment> appointment = patientAppointmentService.getAppointmentsByDoctor(doctorId);
        model.addAttribute("doctorAppointment", appointment);
        return "doctor-appointment-check";
    }

    @PostMapping("/appointment/update/{id}/{status}")
    public String updateAppointmentStatus(@PathVariable Long id, @PathVariable String status) {
        patientAppointmentService.updateAppointmentStatus(id, status);
        return "redirect:/doctor/appointment";
    }
}
