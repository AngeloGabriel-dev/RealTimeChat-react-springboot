package com.rtchat_api.real_time_chat_api.service;

import com.rtchat_api.real_time_chat_api.entity.Room;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.repository.RoomRepository;
import com.rtchat_api.real_time_chat_api.web.dto.roomDto.RoomCreateDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    @Transactional
    public Room criarRoom(Room room){
        return roomRepository.save(room);
    }

    @Transactional
    public void criarRoomEntreAmigos(Room room){roomRepository.save(room);}

    @Transactional(readOnly = true)
    public Room buscarRoomDeAmigos(Long id1, Long id2){
        return roomRepository.findFirstRoomByTwoUsers(id1, id2).orElseThrow(
                () -> new EntityNotFoundException(String.format("Sala com usuário id = %s e id = %s não encontrada.", id1, id2))
        );

    }

    @Transactional
    public void deletarRoom(Room room){
        roomRepository.delete(room);
    }

    public Room adicionarUsersNaRoom(Long roomId, Set<Usuario> users) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Sala com id = %s não encontrada", roomId))
        );

        room.getUsers().addAll(users);
        return  roomRepository.save(room);
    }

    public List<Room> buscarTodasRoomsUsuario(Usuario usuario){
        return roomRepository.findAllByUsuario(usuario);
    }
}
