package com.rtchat_api.real_time_chat_api.repository;

import com.rtchat_api.real_time_chat_api.entity.ChatMessage;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChatMessageRepository {
    public String save(ChatMessage message);
    public List<ChatMessage> findAllChatMessagesByRoomId(Long room_id);
}
