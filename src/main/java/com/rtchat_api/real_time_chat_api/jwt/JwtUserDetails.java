package com.rtchat_api.real_time_chat_api.jwt;

import com.rtchat_api.real_time_chat_api.entity.Usuario;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {
    private Usuario usuario;
    public JwtUserDetails(Usuario usuario){
        super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
        this.usuario = usuario;
        System.out.println(usuario.getId());
    }
    public Long getId(){
        return this.usuario.getId();
    }

    public String getRole(){
        return this.usuario.getRole().name();
    }
}
