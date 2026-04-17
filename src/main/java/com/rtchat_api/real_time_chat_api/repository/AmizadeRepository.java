package com.rtchat_api.real_time_chat_api.repository;

import com.rtchat_api.real_time_chat_api.entity.Amizade;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.web.dto.amizadeDto.AmizadeResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AmizadeRepository extends JpaRepository<Amizade, Long> {
        @Query("""
                SELECT u
                FROM Amizade a
                JOIN Usuario u
                    ON (u.id = a.requester.id OR u.id = a.receiver.id)
                WHERE (a.requester.id = :id OR a.receiver.id = :id)
                AND u.id <> :id
                AND a.status = 'ACEITO'
        """)
        List<Usuario> findFriendsByRequesterIdOrReceiverId(@Param("id") Long id);

        @Query("""
            SELECT new com.rtchat_api.real_time_chat_api.web.dto.amizadeDto.AmizadeResponseDto(
                a.id,
                a.requester.id,
                a.requester.nome,
                a.status
            )
            FROM Amizade a
            WHERE a.receiver.id = :id
              AND a.status = 'PENDENTE'
        """)
        List<AmizadeResponseDto> findPendingReceivedDtos(@Param("id") Long id);

        @Query("""
            SELECT new com.rtchat_api.real_time_chat_api.web.dto.amizadeDto.AmizadeResponseDto(
                a.id,
                a.receiver.id,
                a.receiver.nome,
                a.status
            )
            FROM Amizade a
            WHERE a.requester.id = :id
              AND a.status = 'PENDENTE'
        """)
        List<AmizadeResponseDto> findPendingSentDtos(@Param("id") Long id);

        @Modifying
        @Transactional
        @Query("DELETE FROM Amizade a WHERE (a.requester.id = :userId1 AND a.receiver.id = :userId2) OR (a.requester.id = :userId2 AND a.receiver.id = :userId1)")
        void deleteByUsuario1IdAndUsuario2Id(Long userId1, Long userId2);

        @Query("SELECT a FROM Amizade a WHERE (a.requester.id = :userId1 AND a.receiver.id = :userId2) OR (a.requester.id = :userId2 AND a.receiver.id = :userId1)")
        Optional<Amizade> findByUsuario1IdAndUsuario2Id(Long userId1, Long userId2);

}