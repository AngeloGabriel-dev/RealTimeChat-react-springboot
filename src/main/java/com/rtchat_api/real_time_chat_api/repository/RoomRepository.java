package com.rtchat_api.real_time_chat_api.repository;

import com.rtchat_api.real_time_chat_api.entity.Room;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "SELECT * FROM rooms r " +
            "JOIN room_users ru1 ON r.id = ru1.room_id " +
            "JOIN room_users ru2 ON r.id = ru2.room_id " +
            "WHERE ru1.user_id = :userId1 AND ru2.user_id = :userId2 AND r.nome IS NULL " +
            "LIMIT 1 ",
            nativeQuery = true)
    Optional<Room> findFirstRoomByTwoUsers(Long userId1, Long userId2);

    @Query("SELECT r FROM Room r JOIN r.users u WHERE u = :usuario AND r.nome IS NOT NULL")
    List<Room> findAllByUsuarioAndNomeNotNull(@Param("usuario") Usuario usuario);
}