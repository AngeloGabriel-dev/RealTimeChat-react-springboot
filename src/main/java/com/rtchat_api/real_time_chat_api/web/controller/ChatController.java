package com.rtchat_api.real_time_chat_api.web.controller;


import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import com.rtchat_api.real_time_chat_api.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.ExecutionException;


@Controller
public class ChatController {
    @Autowired
    private ChatMessageService chatMessageService;

    /*public ResponseEntity<MessageDto> send(MessageDto dto) throws Exception{
        Message message = MessageMapper.toMessage(dto);
        return ResponseEntity.ok(dto);
    }*/

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessage chatMessage){
        chatMessage.setTimestamp(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        Long room_id = chatMessage.getRoom_id();
        System.out.println(chatMessage.getContent());
        messagingTemplate.convertAndSend("/topic/room/" + room_id, chatMessage);
        try{
            String message_id = chatMessageService.salvarChatMessage(chatMessage);
        }
        catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
    }


}
