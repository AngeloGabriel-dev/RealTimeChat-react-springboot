package com.rtchat_api.real_time_chat_api.web.dto;


import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class MessageDto {
    private String content;
    private Long sender_id;
    private Long receiver_id;
}
