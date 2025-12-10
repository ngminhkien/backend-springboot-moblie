package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.response.CinemaResponse;
import com.minhkien.mobile.entity.Cinema;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CinemaMapper {

    CinemaResponse toResponse(Cinema cinema);

    List<CinemaResponse> toResponseList(List<Cinema> cinemas);
}

