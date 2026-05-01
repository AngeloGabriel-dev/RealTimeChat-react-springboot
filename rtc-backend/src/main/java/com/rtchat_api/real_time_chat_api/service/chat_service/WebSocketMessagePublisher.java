package com.rtchat_api.real_time_chat_api.service.chat_service;

import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import com.rtchat_api.real_time_chat_api.service.chat_service.MessagePublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketMessagePublisher implements MessagePublisher {
    private final SimpMessagingTemplate template;

    public WebSocketMessagePublisher(SimpMessagingTemplate messagingTemplate){
        this.template = messagingTemplate;
    }

    @Override
    public void publishToRoom(Long roomId, ChatMessage msg) {
        template.convertAndSend("/topic/room/" + roomId, msg);
    }
}
