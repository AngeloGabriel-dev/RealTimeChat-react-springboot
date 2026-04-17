package com.rtchat_api.real_time_chat_api.service;

import com.rtchat_api.real_time_chat_api.entity.Amizade;
import com.rtchat_api.real_time_chat_api.entity.Room;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.repository.AmizadeRepository;
import com.rtchat_api.real_time_chat_api.web.dto.amizadeDto.AmizadeConfirmadaDto;
import com.rtchat_api.real_time_chat_api.web.dto.amizadeDto.AmizadeResponseDto;
import com.rtchat_api.real_time_chat_api.web.dto.mapper.RoomMapper;
import com.rtchat_api.real_time_chat_api.web.dto.mapper.UsuarioMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AmizadeService {
    private final AmizadeRepository amizadeRepository;
    private final RoomService roomService;
    private final UsuarioService usuarioService;


    @Transactional(readOnly = true)
    public List<Usuario> buscarAmigosPorId(Long id){
        List<Usuario> bosta = amizadeRepository.findFriendsByRequesterIdOrReceiverId(id);
        return bosta;
        /*return amizades.stream().filter(amizade -> amizade.getStatus() == Amizade.Status.ACEITO)
                .map(amizade -> {
                    if(amizade.getUsuario1().getId().equals(id)){ return amizade.getUsuario2(); }
                    else{ return amizade.getUsuario1(); }
                }).collect(Collectors.toList());*/
    }

    @Transactional(readOnly = true)
    public List<AmizadeResponseDto> getPendingReceivedFriendshipsById(Long id){
        List<AmizadeResponseDto> amizades = amizadeRepository.findPendingReceivedDtos(id);
        return amizades;
    }

    @Transactional
    public void solicitarAmizade(Long user_id, String username){
        Amizade nova_amizade = new Amizade();
        Usuario requester = usuarioService.buscarPorId(user_id);
        Usuario receiver = usuarioService.buscarPorUsername(username);
        if(amizadeRepository.findByUsuario1IdAndUsuario2Id(requester.getId(), receiver.getId()).isEmpty()){
            nova_amizade.setRequester(requester);
            nova_amizade.setReceiver(receiver);
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
    public AmizadeConfirmadaDto acceptFriendshipById(Long amizade_id, Long user_id) throws AccessDeniedException {
        Amizade amizade = amizadeRepository.findById(amizade_id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Amizade com id = %d não encontrado.", amizade_id))
        );


        if (amizade.getReceiver().getId().equals(user_id) &&
                (amizade.getStatus().equals(Amizade.Status.PENDENTE) ||
                 amizade.getStatus().equals(Amizade.Status.RECUSADO))){

            amizade.setStatus(Amizade.Status.ACEITO);

            Room room = roomService.criarRoomEntreAmigos(amizade);
            amizadeRepository.save(amizade);

            AmizadeConfirmadaDto dto = new AmizadeConfirmadaDto(amizade_id,
                                UsuarioMapper.toDto(amizade.getRequester()),
                                RoomMapper.toDto(room));

            return dto;
        }
        else {
            throw new AccessDeniedException("Usuario não tem permissão para aceitar essa amizade.");
        }


    }
}
