package com.rtchat_api.real_time_chat_api.service;

import com.rtchat_api.real_time_chat_api.entity.Amizade;
import com.rtchat_api.real_time_chat_api.entity.Room;
import com.rtchat_api.real_time_chat_api.entity.RoomUsuario;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.repository.RoomRepository;
import com.rtchat_api.real_time_chat_api.web.dto.roomDto.RoomCreateDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public Room criarRoom(RoomCreateDto dto, Long creatorId){
        Room room = new Room();
        room.setNome(dto.getNome());

        Usuario creator = usuarioService.buscarPorId(creatorId);

        Set<RoomUsuario> relations = new HashSet<>();

        relations.add(new RoomUsuario(room, creator, true));

        Set<Usuario> users = usuarioService.buscarListaUsuariosPorId(dto.getUsers_id());
        for(Usuario user : users){
            relations.add(new RoomUsuario(room, user, false));
        }
        room.setRoom_users(relations);

        return roomRepository.save(room);
    }

    @Transactional
    public void criarRoomEntreAmigos(Amizade amizade){
        Room room = new Room();

        Set<RoomUsuario> relations = new HashSet<>();

        relations.add(new RoomUsuario(room, amizade.getUsuario1(), false));
        relations.add(new RoomUsuario(room, amizade.getUsuario2(), false));

        room.setRoom_users(relations);

        roomRepository.save(room);

    }

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

    public Room addUsersToRoomById(Long roomId, RoomCreateDto dto, Long user_id) throws AccessDeniedException {
        Set<Usuario> users = usuarioService.buscarListaUsuariosPorId(dto.getUsers_id());
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Sala com id = %s não encontrada", roomId))
        );

        Set<RoomUsuario> relations = room.getRoom_users();

        Set<RoomUsuario> admins = relations.stream().filter((ru) -> ru.getUser().getId().equals(user_id)
                                                        && ru.isAdmin()).collect(Collectors.toSet());

        if(admins.isEmpty()){
            throw new AccessDeniedException("Usuário não é admin da room.");
        }

        for(Usuario user:users){
            relations.add(new RoomUsuario(room, user, false));
        }

        room.setRoom_users(relations);

        return roomRepository.save(room);
    }

    public List<Room> buscarTodasRoomsUsuario(Usuario usuario){
        return roomRepository.findAllByUsuario(usuario);
    }
}
