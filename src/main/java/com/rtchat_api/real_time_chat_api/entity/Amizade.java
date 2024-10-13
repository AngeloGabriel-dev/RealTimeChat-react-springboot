package com.rtchat_api.real_time_chat_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "amigo_id", nullable = false)
    private Usuario amigo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 9)
    private Status status = Status.PENDENTE;

    private enum Status{
        PENDENTE, ACEITO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amizade amizade = (Amizade) o;
        return Objects.equals(id, amizade.id) && Objects.equals(usuario, amizade.usuario) && Objects.equals(amigo, amizade.amigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
