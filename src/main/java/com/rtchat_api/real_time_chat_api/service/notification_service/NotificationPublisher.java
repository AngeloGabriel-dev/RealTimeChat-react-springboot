package com.rtchat_api.real_time_chat_api.service.notification_service;

import javax.management.Notification;

public interface NotificationPublisher {
    void notifyUser(String userId, Notification notification);
}
