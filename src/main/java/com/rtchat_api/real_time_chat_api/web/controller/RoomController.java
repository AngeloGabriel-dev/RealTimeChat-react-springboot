package com.rtchat_api.real_time_chat_api.web.controller;

import com.rtchat_api.real_time_chat_api.entity.Room;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.jwt.JwtUserDetails;
import com.rtchat_api.real_time_chat_api.service.RoomService;
import com.rtchat_api.real_time_chat_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/api/v1/rooms")
@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
public class RoomController {
    private final RoomService roomService;
    private final UsuarioService usuarioService;

    @GetMapping("/{room_name}")
    public ResponseEntity<Room> createRoom(@PathVariable String room_name,
                                            @AuthenticationPrincipal JwtUserDetails userDetails){
        Room room = new Room();
        room.setNome(room_name);
        Set<Usuario> users = new HashSet<>();
        users.add(usuarioService.buscarPorId(userDetails.getId()));
        room.setUsers(users);
        return ResponseEntity.ok(roomService.criarRoom(room));
    }

    @GetMapping("/room_friends/{amigo_id}")
    public ResponseEntity<Room> getRoomFriends(@PathVariable Long amigo_id,
                                               @AuthenticationPrincipal JwtUserDetails userDetails){
        Room room = roomService.buscarRoomDeAmigos(userDetails.getId(), amigo_id);
        return ResponseEntity.ok(room);
    }
}
