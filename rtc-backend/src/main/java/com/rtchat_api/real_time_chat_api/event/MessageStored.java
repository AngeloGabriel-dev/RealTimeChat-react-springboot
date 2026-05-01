package com.rtchat_api.real_time_chat_api.event;

import com.rtchat_api.real_time_chat_api.entity.ChatMessage;

public record MessageStored(ChatMessage chatMessage) {
}
