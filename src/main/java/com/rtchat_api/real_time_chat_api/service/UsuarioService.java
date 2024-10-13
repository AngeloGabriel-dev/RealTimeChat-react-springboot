package com.rtchat_api.real_time_chat_api.service;

import com.rtchat_api.real_time_chat_api.entity.Usuario;
import com.rtchat_api.real_time_chat_api.exception.UsernameUniqueViolationException;
import com.rtchat_api.real_time_chat_api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario){
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        }
        catch (org.springframework.dao.DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(String.format("Username {%s} já foi cadastrado.", usuario.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com username = %s não encontrado.", id))
        );
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário com username = %s não encontrado.", username))
        );
    }

    @Transactional(readOnly = true)
    public Usuario.Role buscarRolePorUsername(String username){
        return usuarioRepository.findRoleByUsername(username);
    }
}
