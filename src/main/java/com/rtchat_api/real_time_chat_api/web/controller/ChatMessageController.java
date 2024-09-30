package com.rtchat_api.real_time_chat_api.web.controller;

import com.rtchat_api.real_time_chat_api.entity.ChatMessage;
import com.rtchat_api.real_time_chat_api.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@RequestMapping("/api/v1/chatMessages")
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class ChatMessageController {
    @Autowired
    private final ChatMessageService chatMessageService;

    @GetMapping("/{room_id}")
    public ResponseEntity<?> getChatMessages(@PathVariable Long room_id){
        try {
            List<ChatMessage> messages = chatMessageService.pegarChatMessages(room_id);
            return ResponseEntity.ok(messages);
        } catch (ExecutionException | InterruptedException ex){
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao recuperar mensagens" + ex.getMessage());
        }
    }
}
