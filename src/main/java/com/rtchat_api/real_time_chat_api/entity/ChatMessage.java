package com.rtchat_api.real_time_chat_api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter @Setter
@RequiredArgsConstructor
@ToString
public class ChatMessage {
    private String id;
    private String content;
    private Long sender_id;
    private Long room_id;
    private Long timestamp;
    private States state;

    public enum States {
        DELIVERED, STORED, RECEIVED
    }

}
