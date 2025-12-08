package com.e_health_care.web.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class NotificationWebSocketServer {
    
    private final SimpMessagingTemplate simpMessagingTemplate;

    // Create the contructor for this class to access 
    public NotificationWebSocketServer(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    // Build logic to handle the received destination
    public void sendMessageNotification(String userId, String message) {
        simpMessagingTemplate.convertAndSendToUser(userId, "queue/notifications", message);
    }
}
