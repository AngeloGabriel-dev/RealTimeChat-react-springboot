package com.rtchat_api.real_time_chat_api.entity;

import jakarta.persistence.Embeddable;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Embeddable
@RequiredArgsConstructor
public class RoomUsuarioId implements Serializable {
    public RoomUsuarioId(Long roomId, Long userId){
        this.roomId = roomId;
        this.userId = userId;
    }
    private Long roomId;
    private Long userId;
}
