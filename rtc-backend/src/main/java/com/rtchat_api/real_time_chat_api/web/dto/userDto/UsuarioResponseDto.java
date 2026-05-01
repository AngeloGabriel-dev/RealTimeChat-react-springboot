package com.rtchat_api.real_time_chat_api.web.dto.userDto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioResponseDto {
    private Long id;
    private String username;
    private String nome;
    private String role;

}
