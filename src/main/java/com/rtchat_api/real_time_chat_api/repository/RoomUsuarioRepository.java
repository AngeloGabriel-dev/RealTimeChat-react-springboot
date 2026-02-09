package com.rtchat_api.real_time_chat_api.repository;

import com.rtchat_api.real_time_chat_api.entity.RoomUsuario;
import com.rtchat_api.real_time_chat_api.entity.RoomUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomUsuarioRepository extends JpaRepository<RoomUsuario, RoomUsuarioId> {
}