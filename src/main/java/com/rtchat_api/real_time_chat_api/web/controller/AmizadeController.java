package com.rtchat_api.real_time_chat_api.web.controller;

import com.rtchat_api.real_time_chat_api.entity.Amizade;
import com.rtchat_api.real_time_chat_api.entity.Room;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.jwt.JwtUserDetails;
import com.rtchat_api.real_time_chat_api.service.AmizadeService;
import com.rtchat_api.real_time_chat_api.service.RoomService;
import com.rtchat_api.real_time_chat_api.service.UsuarioService;
import com.rtchat_api.real_time_chat_api.web.dto.UsuarioResponseDto;
import com.rtchat_api.real_time_chat_api.web.dto.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/amizades")
@CrossOrigin(origins = {"http://localhost:3000"})
public class AmizadeController {
    private final AmizadeService amizadeService;
    private final UsuarioService usuarioService;
    private final RoomService roomService;

    @GetMapping("/{username}")
    public void friendRequest(@PathVariable String username,
                              @AuthenticationPrincipal JwtUserDetails userDetails){
        Amizade nova_amizade = new Amizade();
        Room room = new Room();
        Usuario usuario = usuarioService.buscarPorId(userDetails.getId());
        Usuario amigo = usuarioService.buscarPorUsername(username);
        nova_amizade.setUsuario(usuario);
        nova_amizade.setAmigo(amigo);
        Set<Usuario> users = new HashSet<>();
        users.add(nova_amizade.getUsuario());
        users.add(nova_amizade.getAmigo());
        room.setUsers(users);
        roomService.criarRoomEntreAmigos(room);
        amizadeService.solicitarAmizade(nova_amizade);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getFriendsById(@AuthenticationPrincipal
                                                        JwtUserDetails userDetails){
        List<Usuario> amigos = amizadeService.buscarAmigosPorId(userDetails.getId());
        System.out.println(amigos);
        return ResponseEntity.ok(UsuarioMapper.toListDto(amigos));
    }

    @DeleteMapping("/{amigo_id}")
    public void deleteFriendsById(@PathVariable Long amigo_id,
                                  @AuthenticationPrincipal JwtUserDetails userDetails){
        Long usuario_id = userDetails.getId();
        amizadeService.removerAmizadePorId(usuario_id, amigo_id);
    }
}
