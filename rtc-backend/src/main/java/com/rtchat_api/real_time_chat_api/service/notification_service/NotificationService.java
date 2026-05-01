package com.rtchat_api.real_time_chat_api.service.notification_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.Notification;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationPublisher notificationPublisher;

    public void sendNotification(String user_id, Notification notification){
        notificationPublisher.notifyUser(user_id, notification);
    }
}
