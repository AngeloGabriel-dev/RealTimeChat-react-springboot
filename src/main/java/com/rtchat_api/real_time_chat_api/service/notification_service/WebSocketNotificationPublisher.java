package com.rtchat_api.real_time_chat_api.service.notification_service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.management.Notification;

@Component
public class WebSocketNotificationPublisher implements NotificationPublisher{
    private final SimpMessagingTemplate template;

    public WebSocketNotificationPublisher(SimpMessagingTemplate messagingTemplate){
        this.template = messagingTemplate;
    }

    @Override
    public void notifyUser(String userId, Notification notification) {
        template.convertAndSendToUser(userId, "/queue/notifications", notification);
    }
}
