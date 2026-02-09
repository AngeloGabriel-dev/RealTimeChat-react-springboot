package com.rtchat_api.real_time_chat_api.web.dto.mapper;

import com.rtchat_api.real_time_chat_api.entity.Room;
import com.rtchat_api.real_time_chat_api.web.dto.roomDto.RoomResponseDto;
import com.rtchat_api.real_time_chat_api.web.dto.userDto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;
import java.util.stream.Collectors;


public class RoomMapper {
    public static RoomResponseDto toDto(Room room){
        //List<UsuarioResponseDto> users = UsuarioMapper.toListDto(room.getUsers().stream().toList());
        RoomResponseDto roomResponseDto = new ModelMapper().map(room, RoomResponseDto.class);
        //roomResponseDto.setUsers(users);
        return roomResponseDto;
    }

    public static List<RoomResponseDto> toListResponseDto(List<Room> rooms){
        return rooms.stream().map((room)->toDto(room)).collect(Collectors.toList());
    }
}
