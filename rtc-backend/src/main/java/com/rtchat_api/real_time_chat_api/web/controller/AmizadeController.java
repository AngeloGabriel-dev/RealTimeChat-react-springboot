package com.rtchat_api.real_time_chat_api.web.controller;

import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.jwt.JwtUserDetails;
import com.rtchat_api.real_time_chat_api.service.AmizadeService;
import com.rtchat_api.real_time_chat_api.web.dto.amizadeDto.AmizadeConfirmadaDto;
import com.rtchat_api.real_time_chat_api.web.dto.amizadeDto.AmizadeResponseDto;
import com.rtchat_api.real_time_chat_api.web.dto.userDto.UsuarioResponseDto;
import com.rtchat_api.real_time_chat_api.web.dto.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/amizades")
public class AmizadeController {
    private final AmizadeService amizadeService;

    @PostMapping("/{username}")
    public ResponseEntity<Void> friendRequest(@PathVariable String username,
                                              @AuthenticationPrincipal JwtUserDetails userDetails){

        amizadeService.solicitarAmizade(userDetails.getId(), username);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AmizadeConfirmadaDto> acceptFriendship(@PathVariable Long id,
                                                                 @AuthenticationPrincipal JwtUserDetails userDetails) throws AccessDeniedException {
        AmizadeConfirmadaDto dto = amizadeService.acceptFriendshipById(id, userDetails.getId());
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getFriends(@AuthenticationPrincipal
                                                        JwtUserDetails userDetails){
        List<Usuario> amigos = amizadeService.buscarAmigosPorId(userDetails.getId());
        return ResponseEntity.ok(UsuarioMapper.toListDto(amigos));
    }

    @GetMapping("/getPendingReceivedFriendships")
    public ResponseEntity<List<AmizadeResponseDto>> getPendingReceivedFriendships(@AuthenticationPrincipal JwtUserDetails userDetails){
        List<AmizadeResponseDto> pending_friendships = amizadeService.getPendingReceivedFriendshipsById(userDetails.getId());
        return ResponseEntity.ok(pending_friendships);
    }

    @DeleteMapping("/{amigo_id}")
    public ResponseEntity<Void> deleteFriends(@PathVariable Long amigo_id,
                                  @AuthenticationPrincipal JwtUserDetails userDetails){
        amizadeService.removerAmizadePorId(userDetails.getId(), amigo_id);
        return ResponseEntity.noContent().build();
    }
}
