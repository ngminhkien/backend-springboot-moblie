package com.minhkien.mobile.mapper;

import com.minhkien.mobile.dto.request.FilmCreationRequest;
import com.minhkien.mobile.dto.response.FilmResponse;
import com.minhkien.mobile.entity.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    @Mapping(target = "trangThai", ignore = true)
    @Mapping(target = "genres", ignore = true)
    Film toFilm(FilmCreationRequest request);

    // Hàm toFilmResponse đã đúng
    @Mapping(source = "maPhim", target = "maPhim")
    @Mapping(target = "genres",
            expression = "java(film.getGenres().stream().map(g -> g.getMaGenre()).collect(java.util.stream.Collectors.toSet()))")
    FilmResponse toFilmResponse(Film film);
}
