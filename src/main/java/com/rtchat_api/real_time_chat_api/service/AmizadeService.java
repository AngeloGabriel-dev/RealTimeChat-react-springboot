package com.rtchat_api.real_time_chat_api.service;

import com.google.firestore.v1.TransactionOptions;
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
    private final UsuarioService usuarioService;


    @Transactional(readOnly = true)
    public List<Usuario> buscarAmigosPorId(Long id){
        List<Amizade> amizades =  amizadeRepository.findAllByUsuario1IdOrUsuario2Id(id);
        return amizades.stream().filter(amizade -> amizade.getStatus() == Amizade.Status.ACEITO)
                .map(amizade -> {
                    if(amizade.getUsuario1().getId().equals(id)){ return amizade.getUsuario2(); }
                    else{ return amizade.getUsuario1(); }
                }).collect(Collectors.toList());
    }

    @Transactional
    public void solicitarAmizade(Long user_id, String username){
        Amizade nova_amizade = new Amizade();
        Usuario usuario1 = usuarioService.buscarPorId(user_id);
        Usuario usuario2 = usuarioService.buscarPorUsername(username);
        if(amizadeRepository.findByUsuario1IdAndUsuario2Id(usuario1.getId(), usuario2.getId()).isEmpty()){
            nova_amizade.setUsuario1(usuario1);
            nova_amizade.setUsuario2(usuario2);
            amizadeRepository.save(nova_amizade);
        }else{
            System.out.println("Friendship already exists");
        }

    }

    @Transactional(readOnly = true)
    public Amizade getAmizadeByUsersId(Long user_id1, Long user_id2){
        return amizadeRepository.findByUsuario1IdAndUsuario2Id(user_id1, user_id2).orElseThrow(
                () -> new EntityNotFoundException(String.format("Amizade com user_id1 = %d e user_id2 = %d não encontrada.", user_id1, user_id2))
        );
    }

    @Transactional
    public void removerAmizadePorId(Long user_id1, Long user_id2){
        amizadeRepository.deleteByUsuario1IdAndUsuario2Id(user_id1, user_id2);
        roomService.deletarRoom(roomService.buscarRoomDeAmigos(user_id1, user_id2));
    }

    @Transactional
    public Amizade acceptFriendship(Long amizade_id){
        Amizade amizade = amizadeRepository.findById(amizade_id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Amizade com id = %d não encontrado.", amizade_id))
        );
        amizade.setStatus(Amizade.Status.ACEITO);
        Room room = new Room();
        Set<Usuario> users = new HashSet<>();
        users.add(amizade.getUsuario1());
        users.add(amizade.getUsuario2());
        room.setUsers(users);
        roomService.criarRoomEntreAmigos(room);
        return amizadeRepository.save(amizade);
    }
}
