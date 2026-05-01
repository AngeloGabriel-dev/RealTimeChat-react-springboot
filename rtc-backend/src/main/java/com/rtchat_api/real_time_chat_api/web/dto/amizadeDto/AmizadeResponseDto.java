package com.rtchat_api.real_time_chat_api.web.dto.amizadeDto;

import com.rtchat_api.real_time_chat_api.entity.Amizade;
import com.rtchat_api.real_time_chat_api.web.dto.userDto.UsuarioResponseDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AmizadeResponseDto {
    private Long id;
    private Long UsuarioId;
    private String UsuarioNome;
    private Amizade.Status status;
}
