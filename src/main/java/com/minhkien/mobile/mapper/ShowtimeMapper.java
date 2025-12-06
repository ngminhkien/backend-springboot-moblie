package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.response.ShowtimeResponse;
import com.minhkien.mobile.entity.Showtime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShowtimeMapper {

    @Mapping(source = "film.tenPhim", target = "tenPhim")
    @Mapping(source = "cinema.tenRap", target = "tenRap")
    @Mapping(source = "room.tenPhong", target = "tenPhong")
    ShowtimeResponse toResponse(Showtime showtime);

    List<ShowtimeResponse> toResponseList(List<Showtime> showtimes);
}
