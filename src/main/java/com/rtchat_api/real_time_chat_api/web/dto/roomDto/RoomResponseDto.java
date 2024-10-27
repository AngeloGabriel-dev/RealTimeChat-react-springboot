package com.rtchat_api.real_time_chat_api.web.dto.roomDto;

import com.rtchat_api.real_time_chat_api.web.dto.userDto.UsuarioResponseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoomResponseDto {
    @NotBlank
    private String nome;
    private Long id;
    private List<UsuarioResponseDto> users;
}
