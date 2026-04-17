package com.rtchat_api.real_time_chat_api.web.dto.amizadeDto;

import com.rtchat_api.real_time_chat_api.web.dto.roomDto.RoomResponseDto;
import com.rtchat_api.real_time_chat_api.web.dto.userDto.UsuarioResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AmizadeConfirmadaDto {
    private Long id;
    private UsuarioResponseDto friend;
    private RoomResponseDto room;
}
