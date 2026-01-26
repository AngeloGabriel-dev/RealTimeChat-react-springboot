package com.rtchat_api.real_time_chat_api.service.chat_service;

import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import com.rtchat_api.real_time_chat_api.repository.ChatMessageRepository;
import com.rtchat_api.real_time_chat_api.service.notification_service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final MessagePublisher publisher;
    private final NotificationService notificationService;

    public void sendMessage(ChatMessage chatMessage) throws ExecutionException, InterruptedException {
        chatMessage.setTimestamp(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).toEpochSecond(ZoneOffset.of("-03:00")));
        repository.save(chatMessage);
        publisher.publishToRoom(chatMessage.getRoom_id(), chatMessage);
        Notification notification = new Notification("NEW_MESSAGE",
                                                chatMessage.getRoom_id(),
                                                chatMessage.getSender_id(),
                                                chatMessage.getTimestamp(),
                                                chatMessage.getContent());

        notificationService.sendNotification(chatMessage.getSender_id().toString(), notification);
    }

    public String salvarChatMessage(ChatMessage chatMessage) throws ExecutionException, InterruptedException {
        return repository.save(chatMessage);
    }

    public List<ChatMessage> pegarChatMessages(Long room_id) throws ExecutionException, InterruptedException{
        return repository.findAllChatMessagesByRoomId(room_id);
    }
}
