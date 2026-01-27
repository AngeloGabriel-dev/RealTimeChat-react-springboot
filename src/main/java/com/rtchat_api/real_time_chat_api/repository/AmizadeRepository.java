package com.rtchat_api.real_time_chat_api.repository;

import com.rtchat_api.real_time_chat_api.entity.Amizade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AmizadeRepository extends JpaRepository<Amizade, Long> {
        @Query("SELECT a FROM Amizade a WHERE a.usuario1.id = :id OR a.usuario2.id = :id")
        List<Amizade> findAllByUsuario1IdOrUsuario2Id(@Param("id") Long id);

        @Modifying
        @Transactional
        @Query("DELETE a FROM Amizade a WHERE (a.usuario1.id = :userId1 AND a.usuario2.id = :userId2) OR (a.usuario1.id = :userId2 AND a.usuario2.id = :userId1")
        void deleteByUsuario1IdAndUsuario2Id(Long userId1, Long userId2);

        @Query("SELECT a FROM Amizade a WHERE (a.usuario1.id = :userId1 AND a.usuario2.id = :userId2) OR (a.usuario1.id = :userId2 AND a.usuario2.id = :userId1)")
        Optional<Amizade> findByUsuario1IdAndUsuario2Id(Long userId1, Long userId2);

}