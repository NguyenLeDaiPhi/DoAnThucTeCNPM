package com.e_health_care.web.notification.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.e_health_care.web.patient.model.Appointment;
import com.e_health_care.web.patient.repository.PatientAppointmentRepository;

public class AppointmentNotificationService {
    
    @Autowired
    private NotificationWebSocketServer notificationService;

    @Autowired
    private PatientAppointmentRepository appointmentRepository;

    public Appointment confirmAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        // Your logic to update the appointment status
        appointment.setStatus("CONFIRMED");
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Get the patient's ID. This assumes your Appointment entity has a Patient entity
        // The patient's ID needs to be a String to be used with the notification service
        String patientId = Long.toString(updatedAppointment.getPatient().getId());

        // Create a notification message
        String message = "Your appointment with Dr. " + updatedAppointment.getDoctor().getFirstName() + " " 
                + updatedAppointment.getDoctor().getLastName() + " on " + updatedAppointment.getScheduleTime() + " has been confirmed.";
        
        // Send the notification
        notificationService.sendMessageNotification(patientId, message);
        
        return updatedAppointment;
    }
}
