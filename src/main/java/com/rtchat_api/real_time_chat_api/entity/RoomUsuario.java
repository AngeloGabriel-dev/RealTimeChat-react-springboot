package com.rtchat_api.real_time_chat_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter @Setter
@Entity
@RequiredArgsConstructor
public class RoomUsuario {
    @EmbeddedId
    private RoomUsuarioId id;
    public RoomUsuario(Room room, Usuario user, boolean isAdmin){
        setRoom(room);
        setUser(user);
        setAdmin(isAdmin);
        setId(new RoomUsuarioId(room.getId(), user.getId()));
    }

    @ManyToOne
    @MapsId("roomId")
    private Room room;

    @ManyToOne
    @MapsId("userId")
    private Usuario user;

    private boolean isAdmin = false;
}
