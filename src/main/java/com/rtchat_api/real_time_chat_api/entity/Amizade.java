package com.rtchat_api.real_time_chat_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;


@Getter @Setter @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "amizades")
public class Amizade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester", nullable = false)
    private Usuario requester;

    @ManyToOne
    @JoinColumn(name = "receiver", nullable = false)
    private Usuario receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 9)
    private Status status = Status.PENDENTE;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    public enum Status{
        PENDENTE, ACEITO, RECUSADO
    }

    @Override
    public String toString() {
        return "Amizade{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amizade amizade = (Amizade) o;
        return Objects.equals(id, amizade.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
