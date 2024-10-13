package com.rtchat_api.real_time_chat_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter @Setter
@RequiredArgsConstructor
@ToString
public class ChatMessage {
    private String content;
    private Long receiver_id;
    private Long sender_id;
    private Long room_id;
    private Long timestamp;
}
