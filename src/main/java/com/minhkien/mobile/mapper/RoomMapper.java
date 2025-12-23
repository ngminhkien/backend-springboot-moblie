package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.response.RoomResponse;
import com.minhkien.mobile.entity.Room;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    RoomResponse toResponse(Room room);
    List<RoomResponse> toResponseList(List<Room> rooms);
}

