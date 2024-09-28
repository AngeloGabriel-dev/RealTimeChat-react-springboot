package com.rtchat_api.real_time_chat_api.web.controller;

import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.jwt.JwtUserDetails;
import com.rtchat_api.real_time_chat_api.service.UsuarioService;
import com.rtchat_api.real_time_chat_api.web.dto.UsuarioCreateDto;
import com.rtchat_api.real_time_chat_api.web.dto.UsuarioResponseDto;
import com.rtchat_api.real_time_chat_api.web.dto.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.rtchat_api.real_time_chat_api.web.dto.mapper.UsuarioMapper.toDto;

@Tag(name="Usuários", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3000/cadastro"})
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto createDto){
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(user));
    }

    @GetMapping
    public ResponseEntity<UsuarioResponseDto> getByToken(@AuthenticationPrincipal
                                                         JwtUserDetails userDetails){
        Usuario user = usuarioService.buscarPorId(userDetails.getId());
        return ResponseEntity.ok(toDto(user));
    }
}
