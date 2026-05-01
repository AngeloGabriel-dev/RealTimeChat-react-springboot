package com.rtchat_api.real_time_chat_api.listener;

import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import com.rtchat_api.real_time_chat_api.event.MessageStored;
import com.rtchat_api.real_time_chat_api.service.notification_service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.management.Notification;

@Component
@RequiredArgsConstructor
public class MessageNotificationListener {

    private final NotificationService notificationService;

    @EventListener
    public void onMessageStored(MessageStored event) {
        ChatMessage msg = event.chatMessage();
        Notification notification = new Notification(
                "NEW_MESSAGE",
                msg.getRoom_id(),
                msg.getSender_id(),
                msg.getTimestamp(),
                msg.getContent()
        );

        notificationService.sendNotification(
                msg.getSender_id().toString(),
                notification
        );
    }
}