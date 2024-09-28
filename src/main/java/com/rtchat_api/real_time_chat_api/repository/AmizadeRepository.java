package com.rtchat_api.real_time_chat_api.repository;

import com.rtchat_api.real_time_chat_api.entity.Amizade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AmizadeRepository extends JpaRepository<Amizade, Long> {
        List<Amizade> findAllByUsuarioId(Long id);
        @Modifying
        @Transactional
        @Query("DELETE FROM Amizade a WHERE a.usuario.id = :usuarioId AND a.amigo.id = :amigoId")
        void deleteByUsuarioIdAndAmigoId(Long usuarioId, Long amigoId);
}