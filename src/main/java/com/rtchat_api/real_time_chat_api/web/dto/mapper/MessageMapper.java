package com.rtchat_api.real_time_chat_api.web.dto.mapper;

import com.rtchat_api.real_time_chat_api.web.dto.MessageDto;
import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import org.modelmapper.ModelMapper;

public class MessageMapper {
    public static ChatMessage toMessage(MessageDto messageDto){
        return new ModelMapper().map(messageDto, ChatMessage.class);
    }
    public static MessageDto toDto(ChatMessage chatMessage){
        return new ModelMapper().map(chatMessage, MessageDto.class);
    }
}
