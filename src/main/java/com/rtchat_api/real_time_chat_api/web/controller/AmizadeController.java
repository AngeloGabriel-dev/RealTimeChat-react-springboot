package com.rtchat_api.real_time_chat_api.web.controller;

import com.rtchat_api.real_time_chat_api.entity.Amizade;
import com.rtchat_api.real_time_chat_api.entity.Room;
import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.jwt.JwtUserDetails;
import com.rtchat_api.real_time_chat_api.service.AmizadeService;
import com.rtchat_api.real_time_chat_api.service.RoomService;
import com.rtchat_api.real_time_chat_api.service.UsuarioService;
import com.rtchat_api.real_time_chat_api.web.dto.userDto.UsuarioResponseDto;
import com.rtchat_api.real_time_chat_api.web.dto.mapper.UsuarioMapper;
import io.micrometer.core.ipc.http.HttpSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
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

    @PostMapping("/{username}")
    public ResponseEntity<Void> friendRequest(@PathVariable String username,
                                              @AuthenticationPrincipal JwtUserDetails userDetails){

        amizadeService.solicitarAmizade(userDetails.getId(), username);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> acceptFriendship(@PathVariable Long id,
                                                 @AuthenticationPrincipal JwtUserDetails userDetails) throws AccessDeniedException {
        amizadeService.acceptFriendshipById(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getFriendsById(@AuthenticationPrincipal
                                                        JwtUserDetails userDetails){
        List<Usuario> amigos = amizadeService.buscarAmigosPorId(userDetails.getId());
        return ResponseEntity.ok(UsuarioMapper.toListDto(amigos));
    }

    @DeleteMapping("/{amigo_id}")
    public ResponseEntity<Void> deleteFriendsById(@PathVariable Long amigo_id,
                                  @AuthenticationPrincipal JwtUserDetails userDetails){
        Long usuario_id = userDetails.getId();
        amizadeService.removerAmizadePorId(usuario_id, amigo_id);
        return ResponseEntity.noContent().build();
    }
}
