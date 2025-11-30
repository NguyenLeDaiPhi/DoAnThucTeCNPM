package com.e_health_care.web.patient.controller;

import com.e_health_care.web.patient.dto.AppointmentRequestDTO;
import com.e_health_care.web.patient.model.Appointment;
import com.e_health_care.web.patient.model.PatientPrinciple;
import com.e_health_care.web.patient.service.PatientAppointmentService;
import com.e_health_care.web.doctor.model.Doctor;
import com.e_health_care.web.doctor.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/patient/appointment")
public class PatientAppointmentController {

    @Autowired
    private PatientAppointmentService appointmentService;

    @Autowired
    private DoctorRepository doctorRepository;

    // TODO: Cần implement cách lấy patientId từ JWT token hoặc SecurityContext sau khi login
    // Giả sử có method helper để lấy current patient ID từ request
    private Long getCurrentPatientId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof PatientPrinciple principle) {
            return principle.getPatient().getId();
        }
        return null;
    }

    @GetMapping("/book")
    public String bookForm(Model model) {
        AppointmentRequestDTO requestDTO = new AppointmentRequestDTO();
        requestDTO.setPatientId(getCurrentPatientId()); // Set patientId từ context
        model.addAttribute("request", requestDTO);

        // Load danh sách bác sĩ để chọn
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);

        return "patient-appointment-book"; // Template Thymeleaf cho form đặt lịch
    }

    @PostMapping("/book")
    public String bookAppointment(@ModelAttribute AppointmentRequestDTO requestDTO, Model model) {
        // Đảm bảo patientId được set đúng
        requestDTO.setPatientId(getCurrentPatientId());

        try {
            appointmentService.bookAppointment(requestDTO);
            model.addAttribute("success", "Đặt lịch thành công!");
            return "redirect:/patient/appointment/list"; // Redirect đến danh sách lịch hẹn
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            // Reload form với danh sách doctor
            List<Doctor> doctors = doctorRepository.findAll();
            model.addAttribute("doctors", doctors);
            model.addAttribute("request", requestDTO);
            return "patient-appointment-book";
        }
    }

    @GetMapping("/list")
    public String listAppointments(Model model) {
        System.out.println("✅ listAppointments() đã được gọi!");
        Long patientId = getCurrentPatientId();
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);
        model.addAttribute("appointments", appointments);
        return "patient-appointment-list"; // Template hiển thị danh sách lịch hẹn
    }
}