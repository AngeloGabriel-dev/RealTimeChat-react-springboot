package com.rtchat_api.real_time_chat_api.web.controller;


import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import com.rtchat_api.real_time_chat_api.service.chat_service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ExecutionException;


@Controller
public class ChatController {
    @Autowired
    private ChatMessageService chatMessageService;

    @MessageMapping("/chat.sendMessage")
    public void handle(ChatMessage msg) throws ExecutionException, InterruptedException  {
        chatMessageService.sendMessage(msg);
    }
}
