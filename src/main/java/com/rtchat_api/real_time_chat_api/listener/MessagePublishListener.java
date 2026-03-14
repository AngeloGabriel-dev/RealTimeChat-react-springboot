package com.rtchat_api.real_time_chat_api.listener;

import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import com.rtchat_api.real_time_chat_api.event.MessageStored;
import com.rtchat_api.real_time_chat_api.service.chat_service.MessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessagePublishListener {

    private final MessagePublisher publisher;

    @EventListener
    public void onMessageStored(MessageStored event) {
        ChatMessage msg = event.chatMessage();
        publisher.publishToRoom(msg.getRoom_id(), msg);
    }
}
