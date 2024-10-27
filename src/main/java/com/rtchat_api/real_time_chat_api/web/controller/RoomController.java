package com.rtchat_api.real_time_chat_api.web.controller;

import com.rtchat_api.real_time_chat_api.entity.Room;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.jwt.JwtUserDetails;
import com.rtchat_api.real_time_chat_api.service.AmizadeService;
import com.rtchat_api.real_time_chat_api.service.RoomService;
import com.rtchat_api.real_time_chat_api.service.UsuarioService;
import com.rtchat_api.real_time_chat_api.web.dto.roomDto.RoomCreateDto;
import com.rtchat_api.real_time_chat_api.web.dto.userDto.UsuarioCreateDto;
import jakarta.persistence.PostUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class RoomController {
    private final RoomService roomService;
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody @Valid RoomCreateDto dto,
                                            @AuthenticationPrincipal JwtUserDetails userDetails){
        Room room = new Room();
        room.setNome(dto.getNome());
        Set<Usuario> users = usuarioService.buscarListaUsuariosPorId(dto.getUsers_id());
        users.add(usuarioService.buscarPorId(userDetails.getId()));
        room.setUsers(users);
        return ResponseEntity.ok(roomService.criarRoom(room));
    }

    @PutMapping("/addUsersToRoom/{room_id}")
    public ResponseEntity<Room> addUsersToRoom(@PathVariable Long room_id,
                                               @RequestBody @Valid RoomCreateDto dto,
                                               @AuthenticationPrincipal JwtUserDetails userDetails){
        Set<Usuario> users = usuarioService.buscarListaUsuariosPorId(dto.getUsers_id());
        Room room_atualizada = roomService.adicionarUsersNaRoom(room_id, users);
        return ResponseEntity.ok(room_atualizada);
    }

    @GetMapping("/room_friends/{amigo_id}")
    public ResponseEntity<Room> getRoomFriends(@PathVariable Long amigo_id,
                                               @AuthenticationPrincipal JwtUserDetails userDetails){
        Room room = roomService.buscarRoomDeAmigos(userDetails.getId(), amigo_id);
        return ResponseEntity.ok(room);
    }

    public ResponseEntity<List<>>
}
