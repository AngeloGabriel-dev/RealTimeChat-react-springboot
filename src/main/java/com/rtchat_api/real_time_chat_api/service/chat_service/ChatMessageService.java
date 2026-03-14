package com.rtchat_api.real_time_chat_api.service.chat_service;

import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import com.rtchat_api.real_time_chat_api.event.MessageStored;
import com.rtchat_api.real_time_chat_api.repository.ChatMessageRepository;
import com.rtchat_api.real_time_chat_api.service.notification_service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final ApplicationEventPublisher eventPublisher;

    public void sendMessage(ChatMessage chatMessage) throws ExecutionException, InterruptedException {
        chatMessage.setState(ChatMessage.States.RECEIVED);
        chatMessage.setTimestamp(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")).toEpochSecond(ZoneOffset.of("-03:00")));

        System.out.println(chatMessage);

        chatMessage.setState(ChatMessage.States.STORED);

        repository.save(chatMessage);

        eventPublisher.publishEvent(
                new MessageStored(
                        chatMessage
                )
        );
    }

    public String salvarChatMessage(ChatMessage chatMessage) throws ExecutionException, InterruptedException {
        return repository.save(chatMessage);
    }

    public List<ChatMessage> pegarChatMessages(Long room_id) throws ExecutionException, InterruptedException{
        return repository.findAllChatMessagesByRoomId(room_id);
    }
}
