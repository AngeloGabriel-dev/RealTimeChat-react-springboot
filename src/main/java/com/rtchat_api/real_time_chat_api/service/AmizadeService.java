package com.rtchat_api.real_time_chat_api.service;

import com.rtchat_api.real_time_chat_api.entity.Amizade;
import com.rtchat_api.real_time_chat_api.entity.Room;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.repository.AmizadeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AmizadeService {
    private final AmizadeRepository amizadeRepository;
    private final RoomService roomService;

    @Transactional(readOnly = true)
    public List<Usuario> buscarAmigosPorId(Long id){
        System.out.println(id);
        List<Amizade> amizades =  amizadeRepository.findAllByUsuarioId(id);
        System.out.println(amizades);
        return amizades.stream().map(amizade -> amizade.getAmigo()).collect(Collectors.toList());
    }

    @Transactional
    public void solicitarAmizade(Amizade amizade){
        Amizade amizade1 = new Amizade();
        amizade1.setUsuario(amizade.getAmigo());
        amizade1.setAmigo(amizade.getUsuario());
        amizadeRepository.save(amizade);
        amizadeRepository.save(amizade1);
    }

    @Transactional
    public void removerAmizadePorId(Long id_usuario, Long id_amigo){
        amizadeRepository.deleteByUsuarioIdAndAmigoId(id_usuario, id_amigo);
        amizadeRepository.deleteByUsuarioIdAndAmigoId(id_amigo, id_usuario);
    }
}
