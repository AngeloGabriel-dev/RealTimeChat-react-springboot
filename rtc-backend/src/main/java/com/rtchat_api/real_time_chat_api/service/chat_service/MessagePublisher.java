package com.rtchat_api.real_time_chat_api.service.chat_service;

import com.rtchat_api.real_time_chat_api.entity.ChatMessage;

public interface MessagePublisher {
    void publishToRoom(Long roomId, ChatMessage message);
}
