package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.response.GenreResponse;
import com.minhkien.mobile.entity.Genre;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreResponse toGenreResponse(Genre genre);
    List<GenreResponse> toGenreResponseList(List<Genre> genres);
}
