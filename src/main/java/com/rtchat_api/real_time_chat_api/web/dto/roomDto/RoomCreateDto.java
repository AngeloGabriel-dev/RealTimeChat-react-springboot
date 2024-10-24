package com.rtchat_api.real_time_chat_api.web.dto.roomDto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class RoomCreateDto {
    @NotBlank
    private String nome;
    private List<Long> users_id;
}
